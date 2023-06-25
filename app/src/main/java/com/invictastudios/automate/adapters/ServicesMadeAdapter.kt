package com.invictastudios.automate.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.invictastudios.automate.data.model.vehicle_models.PerformedServices
import com.invictastudios.automate.databinding.ServicesMadeItemBinding

class ServicesMadeAdapter(
    private var servicesList: MutableList<PerformedServices>,
    private var onClick: VehicleOnClick
) : RecyclerView.Adapter<ServicesMadeAdapter.MyViewHolder>() {

    fun updateList(newList: List<PerformedServices>) {
        servicesList.clear()
        servicesList.addAll(newList)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder = MyViewHolder(
        ServicesMadeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int = servicesList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.dateTv.append(servicesList[position].performedOn)
        holder.binding.serviceTv.append(servicesList[position].service?.name ?: "")
        holder.binding.mileageTv.append(servicesList[position].mileage)
        holder.itemView.setOnClickListener {
            onClick.onVehicleClick(servicesList[position].id.toString())
        }
    }

    class MyViewHolder(val binding: ServicesMadeItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}