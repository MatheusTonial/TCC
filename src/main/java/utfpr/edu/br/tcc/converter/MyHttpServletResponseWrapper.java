package utfpr.edu.br.tcc.converter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.springframework.util.StreamUtils.BUFFER_SIZE;

public class MyHttpServletResponseWrapper extends HttpServletResponseWrapper {
    private StringWriter sw = new StringWriter(BUFFER_SIZE);

    public MyHttpServletResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    public PrintWriter getWriter() throws IOException {
        return new PrintWriter(sw);
    }

    public ServletOutputStream getOutputStream() throws IOException {
        throw new UnsupportedOperationException();
    }

    public String toString() {
        return sw.toString();
    }
}