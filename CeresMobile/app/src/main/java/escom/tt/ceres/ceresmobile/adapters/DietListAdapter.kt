package escom.tt.ceres.ceresmobile.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import escom.tt.ceres.ceresmobile.R
import escom.tt.ceres.ceresmobile.models.Diet

class DietListAdapter(private var activity: Activity, private var items: MutableList<Diet>) : BaseAdapter() {
  private class ViewHolder(row: View?) {
    var dietDescription: TextView? = null
    var dietAssignDate: TextView? = null

    init {
      this.dietDescription = row?.findViewById(R.id.diet_description)
      this.dietAssignDate = row?.findViewById(R.id.diet_assign_date)
    }
  }

  override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
    val view: View?
    val viewHolder: ViewHolder
    if (convertView == null) {
      val inflater = activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
      view = inflater.inflate(R.layout.patient_diet_row_layout, null)
      viewHolder = ViewHolder(view)
      view?.tag = viewHolder
    } else {
      view = convertView
      viewHolder = view.tag as ViewHolder
    }

    var diet = items[position]
    viewHolder.dietDescription?.text = diet.description
    viewHolder.dietAssignDate?.text = diet.assignDate.toString()

    return view as View
  }

  override fun getItem(i: Int): Diet {
    return items[i]
  }

  override fun getItemId(i: Int): Long {
    return i.toLong()
  }

  override fun getCount(): Int {
    return items.size
  }
}