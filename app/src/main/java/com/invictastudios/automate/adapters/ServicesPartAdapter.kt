package com.invictastudios.automate.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.invictastudios.automate.data.model.vehicle_models.PerformedServices
import com.invictastudios.automate.data.model.vehicle_models.Replacements
import com.invictastudios.automate.databinding.ServicePartsItemBinding
import com.invictastudios.automate.databinding.ServicesMadeItemBinding


class ServicesPartAdapter(
    private var servicesList: MutableList<Replacements>
) : RecyclerView.Adapter<ServicesPartAdapter.MyViewHolder>() {

    fun updateList(newList: List<Replacements>) {
        servicesList.clear()
        servicesList.addAll(newList)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder = MyViewHolder(
        ServicePartsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int = servicesList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.codeTv.append(servicesList[position].part?.code ?: "")
        holder.binding.nameTv.append(servicesList[position].part?.name)
        holder.binding.descriptionTv.append(servicesList[position].part?.description ?: "")
    }

    class MyViewHolder(val binding: ServicePartsItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}