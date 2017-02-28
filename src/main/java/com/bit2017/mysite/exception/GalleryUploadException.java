package com.bit2017.mysite.exception;

public class GalleryUploadException extends RuntimeException {
	private static final long serialVersionUID = 6515137476836806923L;

	public GalleryUploadException() {
		super( "Gallery File Upload Exception" );
	}

	public GalleryUploadException( String message ) {
		super( message );
	}
}
