package com.veeteq.finance.bankdocument.fileupload;

import java.io.InputStream;

import com.veeteq.finance.bankdocument.dto.BankStatementDTO;

public interface UploadProcessor {

    BankStatementDTO process(InputStream inputStream);
}
