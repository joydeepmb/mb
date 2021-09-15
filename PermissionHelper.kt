package pp.bandhan.microbanking.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import app.bandhan.microbanking.util.Utils

class PermissionHelper  constructor() {

    /**
     * Defines types of permission needed in this app
     * @property C camera permission
     * @property R read external storage permission
     * @property W write external storage permission
     */
    enum class Permissions(val permission: String) {
        C(Manifest.permission.CAMERA)
        ,R(Manifest.permission.READ_EXTERNAL_STORAGE)
        ,W(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        ,L(Manifest.permission.ACCESS_COARSE_LOCATION)
        ,M(Manifest.permission.ACCESS_FINE_LOCATION)
    }


    fun checkAllPermissionsGivenOrNot(mContext: Context, vararg permissions: Permissions): Boolean {
        var permArray = BooleanArray(permissions.size)
        permissions.forEach {
            permArray[it.ordinal] = ContextCompat.checkSelfPermission(mContext, it.permission) == PackageManager.PERMISSION_GRANTED
        }
        return false in permArray
    }


    fun askForPermissions(mActivity: Activity): Any? {
        ActivityCompat.requestPermissions(mActivity
            , arrayOf<String>(Permissions.C.permission,
                Permissions.R.permission,
                Permissions.W.permission,
                Permissions.L.permission,
                Permissions.M.permission)
            , Utils.REQ_PERMISSION)
        return null
    }
}
