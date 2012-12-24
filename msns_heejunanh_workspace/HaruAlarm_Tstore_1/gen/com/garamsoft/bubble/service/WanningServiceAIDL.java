/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /Users/garamsoft/dropboxGmail/Dropbox/developspace/msns_heejunanh_workspace/HaruAlarm_Tstore_1/src/com/garamsoft/bubble/service/WanningServiceAIDL.aidl
 */
package com.garamsoft.bubble.service;
public interface WanningServiceAIDL extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.garamsoft.bubble.service.WanningServiceAIDL
{
private static final java.lang.String DESCRIPTOR = "com.garamsoft.bubble.service.WanningServiceAIDL";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.garamsoft.bubble.service.WanningServiceAIDL interface,
 * generating a proxy if needed.
 */
public static com.garamsoft.bubble.service.WanningServiceAIDL asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = (android.os.IInterface)obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.garamsoft.bubble.service.WanningServiceAIDL))) {
return ((com.garamsoft.bubble.service.WanningServiceAIDL)iin);
}
return new com.garamsoft.bubble.service.WanningServiceAIDL.Stub.Proxy(obj);
}
public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_startSoundAndVibrate:
{
data.enforceInterface(DESCRIPTOR);
this.startSoundAndVibrate();
reply.writeNoException();
return true;
}
case TRANSACTION_stopSoundAndVibrate:
{
data.enforceInterface(DESCRIPTOR);
this.stopSoundAndVibrate();
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.garamsoft.bubble.service.WanningServiceAIDL
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
public void startSoundAndVibrate() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_startSoundAndVibrate, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void stopSoundAndVibrate() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_stopSoundAndVibrate, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_startSoundAndVibrate = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_stopSoundAndVibrate = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
}
public void startSoundAndVibrate() throws android.os.RemoteException;
public void stopSoundAndVibrate() throws android.os.RemoteException;
}
