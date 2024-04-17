package com.microservicios.gateway.filters.factory;

public class Configuracion {
	private String mensaje;
	private String cookieNombre;
	private String cookieValor;
	
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getCookieNombre() {
		return cookieNombre;
	}
	public void setCookieNombre(String cookieNombre) {
		this.cookieNombre = cookieNombre;
	}
	public String getCookieValor() {
		return cookieValor;
	}
	public void setCookieValor(String cookieValor) {
		this.cookieValor = cookieValor;
	}
}
