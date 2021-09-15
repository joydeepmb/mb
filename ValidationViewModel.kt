package app.bandhan.microbanking.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class ValidationViewModel: ViewModel() {

    private val userId = MutableStateFlow("")
    private val password = MutableStateFlow("")
    private val customerId = MutableStateFlow("")
    private val productType = MutableStateFlow("")
    private val buId = MutableStateFlow("")
    private val groupId = MutableStateFlow("")
    private val customerName = MutableStateFlow("")
    private val fatherName = MutableStateFlow("")
    private val groupName = MutableStateFlow("")
    private val creditOfficeId = MutableStateFlow("")
    private val amountApplied = MutableStateFlow("")
    private val amountSanctioned = MutableStateFlow("")


    var errorMessage:String?=null

    fun setUserId(user: String) {
        userId.value = user
    }
    fun setPassword(pass: String) {
        password.value = pass
    }

    //GroupLoanIntro Form Validation
    fun setCustomerId(customer_Id: String) {
        customerId.value = customer_Id
    }
    fun setProductType(product_Type: String) {
        productType.value = product_Type
    }
    fun setBUID(bu_Id: String) {
        buId.value = bu_Id
    }
    fun setGroupId(group_Id: String) {
        groupId.value = group_Id
    }
    fun setCustomerName(customer_Name: String) {
        customerName.value = customer_Name
    }
    fun setFatherName(father_Name: String) {
        fatherName.value = father_Name
    }
    fun setGroupName(group_Name: String) {
        groupName.value = group_Name
    }
    fun setCreditOfficeId(creditOffice_Id: String) {
        creditOfficeId.value = creditOffice_Id
    }
    fun setAmountApplied(amount_Applied: String) {
        amountApplied.value = amount_Applied
    }
    fun setAmountSantion(amount_Sanctioned: String) {
        amountSanctioned.value = amount_Sanctioned
    }


    val isSubmitEnabled: Flow<Boolean> = combine(userId,password) { username, password ->

        val regexStringUser = "[a-zA-Z0123456789]+"
        val isUserIdCorrect = username.matches(regexStringUser.toRegex())
        val isUserIdLength = username.length > 4

        val isPasswordCorrect = password.length > 8

        errorMessage = when {
            !isUserIdCorrect -> "UserId Not Valid"
            !isUserIdLength -> "UserId Length Is Not Valid"
            !isPasswordCorrect -> "Password Is Not Valid"
            else -> errorMessage.toString()
        }

        return@combine isUserIdCorrect && isPasswordCorrect
    }

    //GroupLoan Intro Submit
    val isGroupLoanIntroSubmitEnabled: Flow<Boolean> = combine(customerId,productType,buId,groupId) { customerid,_producttype,_buid,_groupid ->


        val isCustomerId = customerid.isNullOrEmpty()
        val isProductType = _producttype.equals("Select Product Type")
        val isBUId = _buid.isNullOrEmpty()
        val isGroupId=_groupid.isNullOrEmpty()

        errorMessage = when {
            !isCustomerId -> "Customer Id Not Valid"
            !isProductType -> "Select Product"
            !isBUId -> "BUID Is Not Empty"
            !isGroupId->"Group id is not Empty"
            else -> errorMessage.toString()
        }

        return@combine isCustomerId and isProductType and isBUId and isGroupId
    }

}