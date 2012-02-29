package com.futurice.tantalum2.rms;

import com.futurice.tantalum2.log.Log;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotFoundException;

/**
 * RMS Utility methods
 *
 * @author ssaa
 */
public class RMSUtils {

    /**
     * Writes a single string value to the record store. Deletes the previous
     * value.
     *
     * @param recordStoreName
     * @param value
     */
    public static void write(final String recordStoreName, final String value) {
        write(recordStoreName, value.getBytes());
    }

    /**
     * Writes the byte array to the record store. Deletes the previous data.
     *
     * @param recordStoreName
     * @param data
     */
    public static void write(final String recordStoreName, final byte[] data) {
        RecordStore rs = null;

        try {
            //delete old value
            try {
                RecordStore.deleteRecordStore(recordStoreName);
            } catch (RecordStoreNotFoundException recordStoreNotFoundException) {
                //ignore
            } catch (RecordStoreException recordStoreException) {
                Log.l.log("RMS delete problem", recordStoreName, recordStoreException);
            }
            rs = RecordStore.openRecordStore(recordStoreName, true);
            rs.addRecord(data, 0, data.length);
        } catch (Exception recordStoreException) {
            Log.l.log("RMS open problem", recordStoreName, recordStoreException);
        } finally {
            try {
                if (rs != null) {
                    rs.closeRecordStore();
                }
            } catch (RecordStoreException recordStoreException) {
                Log.l.log("RMS close problem", recordStoreName, recordStoreException);
            }
        }
    }

    /**
     * Reads a single string value form the given recordstore.
     *
     * @param recordStoreName
     * @return String
     */
    public static String readString(String recordStoreName) {
        final byte[] data = readByteArray(recordStoreName);
        if (data == null) {
            return "";
        }
        return new String(data);
    }

    /**
     * Reads the data from the given recordstore.
     *
     * @param recordStoreName
     * @return byte[]
     */
    public static byte[] readByteArray(String recordStoreName) {
        RecordStore rs = null;
        RecordEnumeration re = null;
        byte[] data = null;

        try {
            rs = RecordStore.openRecordStore(recordStoreName, true);
            re = rs.enumerateRecords(null, null, false);

            if (re.hasNextElement()) {
                data = rs.getRecord(re.nextRecordId());
            }
        } catch (Exception e) {
            Log.l.log("RMS not found", recordStoreName, e);
        } finally {
            if (re != null) {
                re.destroy();
            }

            try {
                if (rs != null) {
                    rs.closeRecordStore();
                }
            } catch (RecordStoreException e) {
                Log.l.log("RMS close error", recordStoreName, e);
            }
        }

        return data;
    }
}
