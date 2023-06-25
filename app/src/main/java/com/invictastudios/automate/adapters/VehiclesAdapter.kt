package com.invictastudios.automate.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.invictastudios.automate.data.model.vehicle_models.VehicleData
import com.invictastudios.automate.data.model.vehicle_models.VehicleModel
import com.invictastudios.automate.databinding.VehicleItemBinding


class VehiclesAdapter(
    private var vehiclesList: MutableList<VehicleData>,
    private var onClick: VehicleOnClick
) : RecyclerView.Adapter<VehiclesAdapter.MyViewHolder>() {

    fun updateList(newList: List<VehicleData>, ) {
        vehiclesList.clear()
        vehiclesList.addAll(newList)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder = MyViewHolder(
        VehicleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int = vehiclesList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.registrationPlateTv.append(vehiclesList[position].registrationPlate)
        holder.binding.brandNameTv.append(vehiclesList[position].model?.brand?.name ?: "")
        holder.binding.modelNameTv.append(vehiclesList[position].model?.name ?: "")
        holder.binding.yearTv.append(vehiclesList[position].model?.year.toString())
        holder.itemView.setOnClickListener {
            onClick.onVehicleClick(vehiclesList[position].id.toString())
        }
    }

    class MyViewHolder(val binding: VehicleItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}