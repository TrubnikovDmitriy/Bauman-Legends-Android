package dv.trubnikov.legends.utils.android

import android.accounts.AccountManager
import android.app.ActivityManager
import android.app.AlarmManager
import android.app.NotificationManager
import android.app.job.JobScheduler
import android.content.ClipboardManager
import android.content.Context
import android.net.ConnectivityManager
import android.view.LayoutInflater
import android.view.WindowManager

fun Context.connectivityManager() = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
fun Context.notificationManager() = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
fun Context.layoutInflater() = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
fun Context.clipboardManager() = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
fun Context.jobScheduler() = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
fun Context.activityManager() = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
fun Context.accountManager() = getSystemService(Context.ACCOUNT_SERVICE) as AccountManager
fun Context.windowManager() = getSystemService(Context.WINDOW_SERVICE) as WindowManager
fun Context.alarmManager() = getSystemService(Context.ALARM_SERVICE) as AlarmManager
