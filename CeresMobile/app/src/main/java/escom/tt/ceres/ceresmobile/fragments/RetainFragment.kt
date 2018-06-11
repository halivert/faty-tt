package escom.tt.ceres.ceresmobile.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.RETAIN

class RetainFragment : Fragment() {
  var mObject: Any = Any()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    retainInstance = true
  }

  companion object {
    fun findOrCreateRetainFragment(fragmentManager: FragmentManager): RetainFragment {
      var retainFragment = fragmentManager.findFragmentByTag(RETAIN) as RetainFragment?

      if (retainFragment == null) {
        retainFragment = RetainFragment()
        fragmentManager.beginTransaction()
            .add(retainFragment, RETAIN).commitAllowingStateLoss()
      }

      return retainFragment
    }
  }
}
