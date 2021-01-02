package com.example.covid19india

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.activity_tracker.view.*
import kotlinx.android.synthetic.main.item_list.view.*

class StateAdapter (val list:List<StatewiseItem>): BaseAdapter(){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView?:LayoutInflater.from(parent.context).inflate(R.layout.item_list,parent,false)
        val item=list[position]
        view.confirmedtv.text=item.confirmed
        view.activetv.text=item.active
        view.recoveredtv.text=item.recovered
        view.deceasedtv.text=item.deaths
        view.statetv.text=item.state
        return view
    }

    override fun getItem(position: Int)=list[position]

    override fun getItemId(position: Int)=position.toLong()

    override fun getCount()=list.size

}