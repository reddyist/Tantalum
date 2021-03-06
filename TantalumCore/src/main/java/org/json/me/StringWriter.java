/*
Copyright (c) 2002 JSON.org

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

The Software shall be used for Good, not Evil.

This variant of the JSON.org code has been sport-tuned for Tantalum Mobile,
https://github.com/TantalumMobile

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package org.json.me;

import java.io.IOException;
import java.io.Writer;

/**
 * A simple StringBuffer-based implementation of StringWriter
 */
public final class StringWriter extends Writer {

    final private StringBuffer buf;

    /**
     * 
     */
    public StringWriter() {
        super();
        buf = new StringBuffer();
    }

    /**
     * 
     * @param initialSize 
     */
    public StringWriter(final int initialSize) {
        super();
        buf = new StringBuffer(initialSize);
    }

    /**
     * 
     * @param cbuf
     * @param off
     * @param len
     * @throws IOException 
     */
    public void write(final char[] cbuf, final int off, final int len) throws IOException {
        buf.append(cbuf, off, len);
    }

    /**
     * 
     * @param str
     * @throws IOException 
     */
    public void write(final String str) throws IOException {
        buf.append(str);
    }

    /**
     * 
     * @param str
     * @param off
     * @param len
     * @throws IOException 
     */
    public void write(final String str, final int off, final int len) throws IOException {
        buf.append(str.substring(off, len));
    }

    /**
     * 
     * @throws IOException 
     */
    public void flush() throws IOException {
    }

    /**
     * 
     * @throws IOException 
     */
    public void close() throws IOException {
    }
}
