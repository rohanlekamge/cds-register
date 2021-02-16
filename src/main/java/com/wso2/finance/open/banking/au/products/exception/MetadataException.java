/*
  Copyright (c) 2021, WSO2 Inc. (http://www.wso2.com). All Rights Reserved.
  This software is the property of WSO2 Inc. and its suppliers, if any.
  Dissemination of any information or reproduction of any material contained
  herein is strictly forbidden, unless permitted by WSO2 in accordance with
  the WSO2 Commercial License available at http://wso2.com/licenses. For specific
  language governing the permissions and limitations under this license,
  please see the license as well as any agreement you’ve entered into with
  WSO2 governing the purchase of this software and any associated services.
 */

package com.wso2.finance.open.banking.au.products.exception;

/**
 * Used for creating checked exceptions that can be handled.
 */
public class MetadataException extends RuntimeException {

    private static final long serialVersionUID = -5686395831712095972L;
    private int errorCode = 500;

    public MetadataException(String message) {
        super(message);
    }

    public MetadataException(String message, Throwable e) {
        super(message, e);
    }

    public MetadataException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

}
