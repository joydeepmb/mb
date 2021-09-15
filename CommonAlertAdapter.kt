package app.bandhan.microbanking.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.bandhan.microbanking.viewholder.GenericViewHolder

abstract class CommonAlertAdapter<T>(val itemLayoutId:Int ,
                                     val items: ArrayList<T>) : RecyclerView.Adapter<GenericViewHolder<T>>() {

    abstract fun bindData(holder: GenericViewHolder<T>, item:T)
    abstract fun clickHanlder (pos: Int,item: T, aView: View) : Unit

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType:Int):   GenericViewHolder<T> {
        return GenericViewHolder(parent,itemLayoutId){ pos, aView ->
            clickHanlder(pos,items.get(pos),aView)
        }
    }

    override fun onBindViewHolder(holder: GenericViewHolder<T>, position: Int) {
        bindData(holder,items.get(position))
    }
}