package com.veeteq.finance.bankdocument.fileupload;

public class UploadProcessorFactory {

	public static UploadProcessor getUploadProcessor(String fileType) throws IllegalArgumentException {
		switch (fileType) {
		case "application/pdf":
			return new CitiBankProcessor();

		default:
			throw new IllegalArgumentException("No processor for selected value");
		}
	}

}
