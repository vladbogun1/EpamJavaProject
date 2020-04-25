package main.java.ua.nure.bogun.epammed.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Properties;

public class Copyright extends TagSupport {
    private static final long serialVersionUID = 1L;

    @Override
    public int doStartTag() throws JspException {
        Properties prop = new Properties();
        String out = null;
        try {
            prop.load(
                    getClass().getClassLoader().getResourceAsStream("project.properties")
            );
            out = prop.getProperty("copyright.label");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            pageContext.getOut().print(out);
        } catch(IOException ioException) {
            throw new JspException("Error: " + ioException.getMessage());
        }
        return SKIP_BODY;
    }
}