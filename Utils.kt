package app.bandhan.microbanking.util

import android.app.Activity
import android.app.Application
import android.app.Dialog
import android.app.Service
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.bandhan.microbanking.R
import app.bandhan.microbanking.adapter.BaseListAdapter
import app.bandhan.microbanking.adapter.CommonAlertAdapter
import app.bandhan.microbanking.dao.*
import app.bandhan.microbanking.db.MBDB
import app.bandhan.microbanking.loan.GroupLoan.GroupLoanIntroFragment
import app.bandhan.microbanking.model.ArgumentLite
import app.bandhan.microbanking.repository.AppRepository
import app.bandhan.microbanking.viewholder.GenericViewHolder
import app.bandhan.microbanking.viewmodel.CommonViewModel
import app.bandhan.microbanking.viewmodel.ViewModelProviderFactory
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import java.math.BigInteger
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


object Utils :OnClickVal{

    lateinit var win_manager: WindowManager
    lateinit var wmlp: WindowManager.LayoutParams
    val REQ_PERMISSION = 101
    var progressDialog: AlertDialog? = null
    private var mb_DB: MBDB? = null
    lateinit var common_view_model:CommonViewModel
    var checked: Boolean=false


    //Network connection check
    fun hasInternetConnection(application: Application): Boolean {
        val connectivityManager = application.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when(type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }

    fun isLaidOut(view: View): Boolean {
        return if (Build.VERSION.SDK_INT >= 19) {
            view.isLaidOut
        } else view.width > 0 && view.height > 0
    }

    // Snakebar shows
    fun showMessage(view: View, message: Any) {
        Snackbar.make(view, message.toString(), Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }
    // handle error message
    fun showErrorMessage(view: View, message: Any) {
        Snackbar.make(view, message.toString(), Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }

    fun initialize_recyler_view(view_recyler: RecyclerView, ctx: Context) :RecyclerView{
        view_recyler!!.layoutManager = LinearLayoutManager(
            ctx,
            LinearLayoutManager.VERTICAL,
            false
        )
        view_recyler.setHasFixedSize(true)
        view_recyler.addItemDecoration(
            DividerItemDecoration(
            ctx,
            DividerItemDecoration.VERTICAL
        )
        )
        return view_recyler
    }
    fun return_data(response_data:String,response_class:Any):Any{
        val data_return = Gson().fromJson(response_data, response_class::class.java)
        return data_return
    }

    fun showProgressDialog(mContext: Context) {
        progressDialog?.let {
            dismissProgressDialog()
        }
        val inflater = (mContext as Activity).layoutInflater
        val view = inflater.inflate(R.layout.custom_progress_dialog, null)
        var progressDialogBuilder = AlertDialog.Builder(
            mContext,
            android.R.style.Theme_Material_Light_NoActionBar_Fullscreen
        )
        progressDialog = progressDialogBuilder.setView(view).create()

        wmlp = progressDialog!!.getWindow()!!.getAttributes();
        wmlp.gravity = Gravity.CENTER;
        val win_manager = mContext.getSystemService(Service.WINDOW_SERVICE) as WindowManager
        val display = win_manager!!.defaultDisplay // getting the screen size of device

        val size = Point()
        display.getSize(size)

        //val width: Int = size.x // Set your heights
       // val height: Int = size.y /2 // set your widths

        val newwidth = (mContext.resources.displayMetrics.widthPixels * 0.99).toInt()
        val newheight = (mContext.resources.displayMetrics.heightPixels * 0.45).toInt()


       // progressDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.WHITE))

        progressDialog?.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            window!!.setLayout(newwidth,newheight)
            window!!.attributes= wmlp
            setCanceledOnTouchOutside(false)
            show()
        }
    }

    fun dismissProgressDialog() {
        try {
            progressDialog?.let {
                if (progressDialog!!.isShowing) {
                    progressDialog!!.dismiss()
                    progressDialog = null
                }
            }
        } catch (e: Exception) { e.printStackTrace() }
    }


    fun common_alert_data(
        ctx: Context,
        data_view: Any,
        storeLivingType: ArrayList<*>,
        title: String
    ) {

        val dialog = Dialog(ctx, R.style.DialogTheme)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.common_alert)
        val ListofInfo = dialog.findViewById(R.id.ListofInfo) as RecyclerView
        val tv_selection = dialog.findViewById(R.id.tv_selection) as TextView
        tv_selection.text = title

        ListofInfo!!.layoutManager = LinearLayoutManager(
            ctx,
            LinearLayoutManager.VERTICAL,
            false
        )
        ListofInfo.setHasFixedSize(false)
        ListofInfo!!.addItemDecoration(
            DividerItemDecoration(
                ctx,
                DividerItemDecoration.VERTICAL
            )
        )
        //storeLivingType as CustomerInfo
        ListofInfo.adapter = object :
            CommonAlertAdapter<Any>(
                R.layout.common_item_row,
                storeLivingType as ArrayList<Any>
            ) {
            override fun bindData(holder: GenericViewHolder<Any>, item: Any) {
                // holder.itemView.tvItem.setText(item.customer_name.toString().capitalize())
                if(item is Product_Info){
                    holder.itemView.findViewById<TextView>(R.id.tvItem).setText(item.fv
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })
                }
                if(item is Occupation_Info){
                    holder.itemView.findViewById<TextView>(R.id.tvItem).setText(item.fv
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })
                }
                if(item is Sector_Code_Info){
                    holder.itemView.findViewById<TextView>(R.id.tvItem).setText(item.fv
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })
                }
                if(item is Sub_Sector_Code_Info){
                    holder.itemView.findViewById<TextView>(R.id.tvItem).setText(item.fv
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })
                }
                if(item is Nature_of_Borrower_Code_Info){
                    holder.itemView.findViewById<TextView>(R.id.tvItem).setText(item.fv
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })
                }
                if(item is Family_Member_Info){
                    holder.itemView.findViewById<TextView>(R.id.tvItem).setText(item.fv
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })
                }
                if(item is AccountRelationShip_Info){
                    holder.itemView.findViewById<TextView>(R.id.tvItem).setText(item.fv
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })
                }
                if(item is ModeOfOperation_Info){
                    holder.itemView.findViewById<TextView>(R.id.tvItem).setText(item.fv
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })
                }
            }

            override fun clickHanlder(pos: Int, item: Any, aView: View) {
                if(item is Product_Info){
                    var show_text: TextView =data_view as TextView
                    show_text.setText(item.fv)
                    dialog.dismiss()
                }else if(item is Occupation_Info){
                    var show_text: TextView =data_view as TextView
                    show_text.setText(item.fv)
                    dialog.dismiss()
                }else if(item is Sector_Code_Info){
                    var show_text: TextView =data_view as TextView
                    show_text.setText(item.fv)
                    dialog.dismiss()
                }else if(item is Sub_Sector_Code_Info){
                    var show_text: TextView =data_view as TextView
                    show_text.setText(item.fv)
                    dialog.dismiss()
                }else if(item is Nature_of_Borrower_Code_Info){
                    var show_text: TextView =data_view as TextView
                    show_text.setText(item.fv)
                    dialog.dismiss()
                }else if(item is Family_Member_Info){
                    var show_text: TextView =data_view as TextView
                    show_text.setText(item.fv)
                    dialog.dismiss()
                }else if(item is AccountRelationShip_Info){
                    var show_text: TextView =data_view as TextView
                    show_text.setText(item.fv)
                    dialog.dismiss()
                }else if(item is ModeOfOperation_Info){
                    var show_text: TextView =data_view as TextView
                    show_text.setText(item.fv)
                    dialog.dismiss()
                }
            }
        }
        val ibAlertClose = dialog.findViewById(R.id.ibAlertClose) as ImageButton
        ibAlertClose.setOnClickListener {
            dialog.dismiss()
        }
        wmlp = dialog.getWindow()!!.getAttributes();
        wmlp.gravity = Gravity.BOTTOM;
        val win_manager = ctx.getSystemService(Service.WINDOW_SERVICE) as WindowManager
        val display = win_manager!!.defaultDisplay // getting the screen size of device

        val size = Point()
        display.getSize(size)

        //val width: Int = size.x // Set your heights
        //val height: Int = size.y / 2 // set your widths

        val newwidth = (ctx.resources.displayMetrics.widthPixels * 0.99).toInt()
        val newheight = (ctx.resources.displayMetrics.heightPixels * 0.99).toInt()

        dialog.window!!.setLayout(newwidth, newheight)
        dialog.window!!.attributes = wmlp
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        //dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation_resendotp
        dialog.show()
        //return fetch_living_source_details
    }

    // alert for save data
    fun alert_for_prefield_view_save_data(
        ctx: Context,
        storeLivingType: ArrayList<*>,
        itemClickListener: OnClickVal
    ) {

        val dialog = Dialog(ctx, R.style.DialogTheme)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.common_save_item_row)

        val btn_continue = dialog.findViewById<Button>(R.id.btnContinue)
        val btnAlertCancel = dialog.findViewById<Button>(R.id.btnAlertCancel)
        val tv_customer_name=dialog.findViewById<TextView>(R.id.tv_customer_name)
        val tv_spouse_name=dialog.findViewById<TextView>(R.id.tv_spouse_name)
        val tv_group_name=dialog.findViewById<TextView>(R.id.tv_group_name)
        val tv_amount_applied=dialog.findViewById<TextView>(R.id.tv_amount_applied)
        val tv_amount_santioned=dialog.findViewById<TextView>(R.id.tv_amount_santioned)
        val tv_maturity_date=dialog.findViewById<TextView>(R.id.tv_maturity_date)
        val tv_installment_amount=dialog.findViewById<TextView>(R.id.tv_installment_amount)

        storeLivingType.forEach {
            if(it is ArgumentLite){
                when(it.fn){
                    "@customerName"->{
                        tv_customer_name.setText(it.fv)
                    }
                    "@spouseName"->{
                        tv_spouse_name.setText(it.fv)
                    }
                    "@groupName"->{
                        tv_group_name.setText(it.fv)
                    }
                    "@amountApplied"->{
                        tv_amount_applied.setText(it.fv)
                    }
                    "@amountSanctioned"->{
                        tv_amount_santioned.setText(it.fv)
                    }
                    "@maturityDate"->{
                        tv_maturity_date.setText(it.fv)
                    }
                    "@installmentAmount"->{
                        tv_installment_amount.setText(it.fv)
                    }
                }
            }
        }

        //tv_selection.text = title
        btn_continue.setOnClickListener {
            dialog.dismiss()
            itemClickListener.onClick("continue")
        }
        btnAlertCancel.setOnClickListener {
            dialog.dismiss()
        }

        wmlp = dialog.getWindow()!!.getAttributes();
        wmlp.gravity = Gravity.BOTTOM;
        val win_manager = ctx.getSystemService(Service.WINDOW_SERVICE) as WindowManager
        val display = win_manager!!.defaultDisplay // getting the screen size of device

        val size = Point()
        display.getSize(size)

        //val width: Int = size.x // Set your heights
        //val height: Int = size.y / 2 // set your widths

        val newwidth = (ctx.resources.displayMetrics.widthPixels * 0.99).toInt()
        val newheight = (ctx.resources.displayMetrics.heightPixels * 0.99).toInt()

        dialog.window!!.setLayout(newwidth, newheight)
        dialog.window!!.attributes = wmlp
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        //dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation_resendotp
        dialog.show()
        //return fetch_living_source_details
    }



    fun String.sha256(): String {
        val md = MessageDigest.getInstance("SHA-256")
        var s = BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
        var sf3 = ""

        sf3 = String.format("%64s", s) //returns 12 char fractional part filling with 0
        sf3 = sf3.replace(" ", "0")

        return sf3
    }

    override fun onClick(item: Any) {
        if (item is String)
        println("item clicked"+item.toString())

    }

    fun getDatewithMonth(): String {
        val date: String = SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().time)
        val time: String = SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().time)
        return date
    }
    /*fun CheckAllFields(etCustomerId: EditText,
                       product_type:String,
                       tv_product_type:TextView,
                       product_type_details: FrameLayout,
                       etBUId:EditText,
                       etAmountApplied:EditText,
                       etAmountSanctioned:EditText,
                       textInputCustomerId: TextInputLayout,
                       textInputBUID:TextInputLayout,
                       textInputAmountApplied:TextInputLayout, textAmountSanctioned:TextInputLayout
    ): Boolean {
        if (etCustomerId.length() === 0) {
            etCustomerId.setError("Customer Id required")
            textInputCustomerId.apply {
                setBoxStrokeColorStateList(Utils.change_background_color_list())
            }
            return false
        }else{
            etCustomerId.setError(null)
            textInputCustomerId.apply {
                setBoxStrokeColorStateList(Utils.edit_text_background_color_list())
            }
        }
        if(product_type.equals("Select Product Type:")){
            tv_product_type.setError("Select Product Type")

        }else{
            tv_product_type.setError(null)
        }
        if (etBUId.length() === 0) {
            etBUId.setError("BU ID is required")
            textInputBUID.apply {
                setBoxStrokeColorStateList(Utils.change_background_color_list())
            }
            return false
        }else{
            etBUId.setError(null)
            textInputBUID.apply {
                setBoxStrokeColorStateList(Utils.edit_text_background_color_list())
            }
        }
        if (etAmountApplied.length() === 0 || etAmountApplied.getText().toString().startsWith("0")) {
            etAmountApplied.setError("Amount Applied required")
            textInputAmountApplied.apply {
                setBoxStrokeColorStateList(Utils.change_background_color_list())
            }
            return false
        }else{
            etAmountApplied.setError(null)
            textInputAmountApplied.apply {
                setBoxStrokeColorStateList(Utils.edit_text_background_color_list())
            }
        }
        if (etAmountSanctioned.length() === 0 || etAmountSanctioned.getText().toString().startsWith("0")) {
            etAmountSanctioned.setError("Fill Amount Sanctioned ")
            textAmountSanctioned.apply {
                setBoxStrokeColorStateList(Utils.change_background_color_list())
            }
            return false
        }else{
            etAmountSanctioned.setError(null)
            textAmountSanctioned.apply {
                setBoxStrokeColorStateList(Utils.edit_text_background_color_list())
            }
        }

        // after all validation return true.
        return true
    }*/



    fun change_background_color_list():ColorStateList{
        val color: Int = Color.rgb(255, 0, 0)
        //Color from hex string
        //Color from hex string
        val color2: Int = Color.parseColor("#D5241C")

        val states = arrayOf(
            intArrayOf(android.R.attr.state_focused),
            intArrayOf(android.R.attr.state_hovered),
            intArrayOf(android.R.attr.state_enabled),
            intArrayOf()
        )

        val colors = intArrayOf(
            color,
            color,
            color,
            color2
        )
        var display_color_list= ColorStateList(states, colors)
        return display_color_list

    }
    fun edit_text_background_color_list():ColorStateList{
        val color: Int = Color.rgb(255, 0, 0)
        //Color from hex string
        //Color from hex string
        val color2: Int = Color.parseColor("#000000")

        val states = arrayOf(
            intArrayOf(android.R.attr.state_focused),
            intArrayOf(android.R.attr.state_hovered),
            intArrayOf(android.R.attr.state_enabled),
            intArrayOf()
        )

        val colors = intArrayOf(
            color,
            color,
            color,
            color2
        )
        var display_color_list= ColorStateList(states, colors)
        return display_color_list

    }


    // initialization
    fun initialization(ctx: Context,_act:FragmentActivity) {
        mb_DB = MBDB.getDataBase(ctx)
        val repository = AppRepository()
        val factory = ViewModelProviderFactory(_act.application, repository,ctx)
        // customer_view_model= ViewModelProvider(requireActivity(), factory).get(CustomerViewModel::class.java)
        common_view_model= ViewModelProvider(_act, factory).get(CommonViewModel::class.java)
        //customer_view_model.initialize_db(mb_DB!!,requireContext())
        common_view_model.initialize_db(mb_DB!!,ctx)
    }

    // validation of group loan info
    // Group Loan Intro Validation
    fun CheckAllFields(etCustomerId: EditText,
                       _productType:String,
                       tvProductType:TextView,
                       fmProductType:FrameLayout,
                       etBUId:EditText,
                       etAmountApplied:EditText,
                       etAmountSanctioned:EditText,
                       textInputCustomerId: TextInputLayout,
                       textInputBUID:TextInputLayout,
                       textInputAmountApplied:TextInputLayout, textAmountSanctioned:TextInputLayout

    ): Boolean {

        if (etCustomerId.length() === 0) {
            etCustomerId.setError("Customer Id required")
            textInputCustomerId.apply {
                setBoxStrokeColorStateList(Utils.change_background_color_list())
            }
            return false
        }else{
            etCustomerId.setError(null)
            textInputCustomerId.apply {
                setBoxStrokeColorStateList(Utils.edit_text_background_color_list())
            }
        }
        if(_productType.equals("Select Product Type:",true)){
            tvProductType.setError("Select Product Type")
            return false
        }else{
            tvProductType.setError(null)
        }
        if (etBUId.length() === 0) {
            etBUId.setError("BU ID is required")
            textInputBUID.apply {
                setBoxStrokeColorStateList(Utils.change_background_color_list())
            }
            return false
        }else{
            etBUId.setError(null)
            textInputBUID.apply {
                setBoxStrokeColorStateList(Utils.edit_text_background_color_list())
            }
        }
        if (etAmountApplied.length() === 0 || etAmountApplied.getText().toString().startsWith("0")) {
            etAmountApplied.setError("Amount Applied required")
            textInputAmountApplied.apply {
                setBoxStrokeColorStateList(Utils.change_background_color_list())
            }
            return false
        }else{
            etAmountApplied.setError(null)
            textInputAmountApplied.apply {
                setBoxStrokeColorStateList(Utils.edit_text_background_color_list())
            }
        }
        if (etAmountSanctioned.length() === 0 || etAmountSanctioned.getText().toString().startsWith("0")) {
            etAmountSanctioned.setError("Fill Amount Sanctioned ")
            textAmountSanctioned.apply {
                setBoxStrokeColorStateList(Utils.change_background_color_list())
            }
            return false
        }else{
            etAmountSanctioned.setError(null)
            textAmountSanctioned.apply {
                setBoxStrokeColorStateList(Utils.edit_text_background_color_list())
            }
        }

        // after all validation return true.
        return true
    }

    //Group Loan Term Validation
    fun checkTermLoanField(etLoanInterestRate:EditText,textInputLoanInterestRate:TextInputLayout ,
                           etTerm : EditText , textInputTerm : TextInputLayout , etTenureType : EditText , textinputTenureType : TextInputLayout,
                           etMaturityDate : EditText , textInputMaturityDate : TextInputLayout , etInstallmentAmmount : EditText , textInputInstallmentAmmount : TextInputLayout,
                           _SectorCode : String,tvSectorCode : TextView,_SubSectorCode : String,tvSubSectorCode : TextView ,
                           _Occupation : String,tvOccupation : TextView,etLandHolding : EditText , textInputLandHolding : TextInputLayout,_InvestmentIntPlant : String,
                           tvInvestmentInPlant : TextView,_NatureOfBorrowerAmount : String , tvNatureOfBorrowerAmount : TextView

    ) : Boolean{
        if (etLoanInterestRate.length() === 0 || etLoanInterestRate.getText().toString().startsWith("0")) {
            etLoanInterestRate.setError("Loan Interest Rate required")
            textInputLoanInterestRate.apply {
                setBoxStrokeColorStateList(Utils.change_background_color_list())
            }
            return false
        }else{
            etLoanInterestRate.setError(null)
            textInputLoanInterestRate.apply {
                setBoxStrokeColorStateList(Utils.edit_text_background_color_list())
            }
        }
        if (etTerm.length() === 0) {
            etTerm.setError("Term required")
            textInputTerm.apply {
                setBoxStrokeColorStateList(Utils.change_background_color_list())
            }
            return false
        }else{
            etTerm.setError(null)
            textInputTerm.apply {
                setBoxStrokeColorStateList(Utils.edit_text_background_color_list())
            }
        }
        if (etTenureType.length() === 0) {
            etTenureType.setError("Tenure Type required")
            textinputTenureType.apply {
                setBoxStrokeColorStateList(Utils.change_background_color_list())
            }
            return false
        }else{
            etTenureType.setError(null)
            textinputTenureType.apply {
                setBoxStrokeColorStateList(Utils.edit_text_background_color_list())
            }
        }
        if (etMaturityDate.length() === 0) {
            etMaturityDate.setError("Maturity Date required")
            textInputMaturityDate.apply {
                setBoxStrokeColorStateList(Utils.change_background_color_list())
            }
            return false
        }else{
            etMaturityDate.setError(null)
            textInputMaturityDate.apply {
                setBoxStrokeColorStateList(Utils.edit_text_background_color_list())
            }
        }
        if (etInstallmentAmmount.length() === 0 || etInstallmentAmmount.getText().toString().startsWith("0")) {
            etInstallmentAmmount.setError("Installment Ammount required")
            textInputInstallmentAmmount.apply {
                setBoxStrokeColorStateList(Utils.change_background_color_list())
            }
            return false
        }else{
            etInstallmentAmmount.setError(null)
            textInputInstallmentAmmount.apply {
                setBoxStrokeColorStateList(Utils.edit_text_background_color_list())
            }
        }
        if (_SectorCode.equals("Select Sector Code:",true)) {
            tvSectorCode.setError("Select Sector Code")
            return false
        }else{
            tvSectorCode.setError(null)
        }
        if(_SubSectorCode.equals("Select Sub Sector Code:",true)){
            tvSubSectorCode.setError("Select Sub Sector Code")
            return false
        }else{
            tvSubSectorCode.setError(null)
        }
        if(_Occupation.equals("Select Occupation Code:",true)){
            tvOccupation.setError("Select Occupation Code")
            return false
        }else{
            tvOccupation.setError(null)
        }

        if(_NatureOfBorrowerAmount.equals("Nature of Borrower Amount",true)){
            tvNatureOfBorrowerAmount.setError("Select Nature Of Borrower Amount")
            return false
        }else{
            tvNatureOfBorrowerAmount.setError(null)
        }
        if (etLandHolding.length() === 0 || etLandHolding.getText().toString().startsWith("0")) {
            etLandHolding.setError("Land Holding required")
            textInputLandHolding.apply {
                setBoxStrokeColorStateList(Utils.change_background_color_list())
            }
            return false
        }else{
            etLandHolding.setError(null)
            textInputLandHolding.apply {
                setBoxStrokeColorStateList(Utils.edit_text_background_color_list())
            }
        }
        return true
    }

    //Group Loan Account

    fun checkLoanAccountList(etOpeningDate : EditText,
                             textInputOpeningDate : TextInputLayout,
                             is_disbursement_loan:Boolean,
                             etSavingAccount : EditText ,
                             TextInputSavingAccount : TextInputLayout ,
                             _familyMember:String,textviewFamilyMember:TextView,
                             etCreditBureauRefNumber : EditText ,
                             textInputCreditBureauNumber:TextInputLayout,
                             is_water_purifier_check:Boolean,
                             is_disbursement_mode:Boolean
    ) : Boolean {
        if (etOpeningDate.length() === 0) {
            etOpeningDate.setError("Opening Date Required")
            textInputOpeningDate.apply {
                setBoxStrokeColorStateList(Utils.change_background_color_list())
            }
            return false
        } else {
            etOpeningDate.setError(null)
            textInputOpeningDate.apply {
                setBoxStrokeColorStateList(Utils.edit_text_background_color_list())
            }
        }
        if(is_disbursement_loan==false)
            return false

        if (etSavingAccount.length() === 0 || etSavingAccount.getText().toString().startsWith("0")) {
            etSavingAccount.setError("Saving Account Required")
            TextInputSavingAccount.apply {
                setBoxStrokeColorStateList(Utils.change_background_color_list())
            }
            return false
        } else {
            etSavingAccount.setError(null)
            TextInputSavingAccount.apply {
                setBoxStrokeColorStateList(Utils.edit_text_background_color_list())
            }
        }
        if (_familyMember.equals("Select Family member", true)) {
            textviewFamilyMember.setError("Select Family Member")
            return false
        }else{
            textviewFamilyMember.setError(null)
        }
        if (etCreditBureauRefNumber.length() === 0 || etCreditBureauRefNumber.getText().toString().startsWith("0")){
            etCreditBureauRefNumber.setError("Credit Bureau Ref. Number Required")
            textInputCreditBureauNumber.apply {
                setBoxStrokeColorStateList(Utils.change_background_color_list())
            }
            return false
        }else{
            etCreditBureauRefNumber.setError(null)
            textInputCreditBureauNumber.apply {
                setBoxStrokeColorStateList(Utils.edit_text_background_color_list())
            }
        }
        if(is_water_purifier_check==false)
            return false
        if(is_disbursement_mode==false)
            return false

        return true
    }
    //Checked Error Validation
    fun onCheckBoxClicked(view: View,text:TextInputLayout):Boolean {
        if (view is CheckBox) {
           checked= view.isChecked
            when (view) {
                view -> {
                    if (!checked) {
                        text.setError("Checked Required")
                    }else{
                        checked=true
                        text.setError(null)
                    }
                }
            }
        }
        return checked
    }


}