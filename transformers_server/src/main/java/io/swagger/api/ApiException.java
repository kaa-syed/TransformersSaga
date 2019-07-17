package io.swagger.api;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-07-11T12:42:06.333Z[GMT]")
public class ApiException extends Exception{
    /**
	 * 
	 */
	private static final long serialVersionUID = -2174637728997742359L;
	private int code;
    public ApiException (int code, String msg) {
        super(msg);
        this.code = code;
    }
	public int getCode() {
		return code;
	}
}
