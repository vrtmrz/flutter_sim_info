package flutter.moum.sim_info;

import android.content.Context;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

class MethodHandlerImpl implements MethodChannel.MethodCallHandler {

    Context context;
    private TelephonyManager mTelephonyManager;
    public void setContext(Context context){
        this.context = context;
        if(context!=null) {
            mTelephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        }else{
            mTelephonyManager = null;
        }

    }
    public MethodHandlerImpl() {

    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        if (!isSimStateReady()) {
            result.error("SIM_STATE_NOT_READY", null, null);
            return;
        }

        switch (call.method) {
            case "allowsVOIP":
                result.success(true);
                break;
            case "carrierName":
                result.success(getCarrierName());
                break;
            case "isoCountryCode":
                result.success(getIsoCountryCode());
                break;
            case "mobileCountryCode":
                result.success(getMobileCountryCode());
                break;
            case "mobileNetworkCode":
                result.success(getMobileNetworkCode());
                break;
            default:
                result.notImplemented();
        }
    }
    @SuppressWarnings("SameParameterValue")
    private boolean hasPermission(String permission) {
        return PackageManager.PERMISSION_DENIED ==
                ContextCompat.checkSelfPermission(context, permission);
    }

    private boolean isSimStateReady() {
        if(mTelephonyManager==null) return false;
        return TelephonyManager.SIM_STATE_READY == mTelephonyManager.getSimState();
    }

    private String getCarrierName() {
        if(mTelephonyManager==null) return "null";
        String networkOperatorName = mTelephonyManager.getNetworkOperatorName();
        if (networkOperatorName == null) return "null";
        return networkOperatorName;
    }
    private String getIsoCountryCode() {
        if(mTelephonyManager==null) return "null";
        String networkCountryIso = mTelephonyManager.getNetworkCountryIso();
        if (networkCountryIso == null) return "null";
        return networkCountryIso;
    }
    private String getMobileCountryCode() {
        if(mTelephonyManager==null) return "null";
        if (getMccMnc() == null || getMccMnc().length() < 5) return "null";
        return getMccMnc().substring(0, 3);
    }
    private String getMobileNetworkCode() {
        if(mTelephonyManager==null) return "null";
        if (getMccMnc() == null || getMccMnc().length() < 5) return "null";
        return getMccMnc().substring(3);
    }

    private String getMccMnc() {
        if(mTelephonyManager==null) return "null";
        return mTelephonyManager.getNetworkOperator();
    }
}
