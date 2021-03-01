package flutter.moum.sim_info;
import android.content.Context;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry.Registrar;


/** SimInfoPlugin */
public class SimInfoPlugin implements FlutterPlugin, ActivityAware {
  private MethodChannel channel;
  private MethodHandlerImpl handler = new MethodHandlerImpl();


  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    SimInfoPlugin plugin = new SimInfoPlugin();
    plugin.setMethodChannel(registrar.context(), registrar.messenger());
  }

  private void setMethodChannel(Context context, BinaryMessenger messenger) {
    channel = new MethodChannel(messenger, "flutter.moum.sim_info");
    if(context != null) handler.setContext(context);
    channel.setMethodCallHandler(handler);

  }

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding binding) {
    SimInfoPlugin plugin = new SimInfoPlugin();

    final Context context = binding.getApplicationContext();
    plugin.setMethodChannel(context, binding.getBinaryMessenger());
    if(context != null) handler.setContext(context);
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    handler.setContext(null);
  }

  @Override
  public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
    final Context context = binding.getActivity();
    if(context != null) handler.setContext(context);
  }

  @Override
  public void onDetachedFromActivityForConfigChanges() {
    handler.setContext(null);
  }

  @Override
  public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {
    final Context context = binding.getActivity();
    if(context != null) handler.setContext(context);
  }

  @Override
  public void onDetachedFromActivity() {
    handler.setContext(null);
  }
}
