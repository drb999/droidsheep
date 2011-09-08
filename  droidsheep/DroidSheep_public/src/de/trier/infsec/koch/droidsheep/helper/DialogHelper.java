package de.trier.infsec.koch.droidsheep.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;
import de.trier.infsec.koch.droidsheep.R;
import de.trier.infsec.koch.droidsheep.auth.AuthHelper;

public class DialogHelper {

	private static Activity context = null;

	public static void installBusyBox(Activity context) {
		DialogHelper.context = context;
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(R.string.installbusybox).setCancelable(false)
				.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						Intent goToMarket = null;
						goToMarket = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=stericson.busybox"));
						DialogHelper.context.startActivity(goToMarket);
						dialog.cancel();
					}
				}).setNegativeButton(R.string.button_abprt, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	public static void clearBlacklist(Activity context) {
		DialogHelper.context = context;
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(R.string.clear_blacklist).setCancelable(false)
				.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						DBHelper.clearBlacklist(DialogHelper.context);
						AuthHelper.clearBlacklist();
					}
				}).setNegativeButton(R.string.button_abprt, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	public static void showUnrooted(Activity context) {
		DialogHelper.context = context;
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(R.string.unrooted).setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

	private static boolean disclaimerAccepted = false;

	public static void showDisclaimer(Activity context) {
		DialogHelper.context = context;

		if (DialogHelper.disclaimerAccepted)
			return;

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.disclaimer, (ViewGroup) context.findViewById(R.id.layout_root));

		AlertDialog al = new AlertDialog(context) {
			@Override
			public boolean onSearchRequested() {
				return false;
			}
		};
		al.setView(layout);
		al.setButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				CheckBox cb = (CheckBox) layout.findViewById(R.id.lic_ack);
				if (!cb.isChecked()) {
					Toast t = Toast.makeText(DialogHelper.context, DialogHelper.context.getString(R.string.accept_text),
							Toast.LENGTH_SHORT);
					t.show();
					DialogHelper.showDisclaimer(DialogHelper.context);
				} else {
					disclaimerAccepted = true;
				}
			}
		});
		al.setCancelable(false);
		al.show();
	}

}