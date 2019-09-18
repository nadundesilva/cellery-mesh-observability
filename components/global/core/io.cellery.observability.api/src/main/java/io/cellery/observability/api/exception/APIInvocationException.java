/*
 * Copyright (c) 2019, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.cellery.observability.api.exception;

import javax.ws.rs.core.Response;

/**
 * Internal generic error in the API.
 */
public class APIInvocationException extends Exception {

    public APIInvocationException(String message, Throwable e) {
        super(message, e);
    }

    /**
     * Exception mapper for APIInvocationException.
     */
    public static class Mapper extends BaseExceptionMapper<APIInvocationException> {

        public Mapper() {
            super(Response.Status.INTERNAL_SERVER_ERROR);
        }

        @Override
        public Response toResponse(APIInvocationException exception) {
            return generateResponse(exception);
        }
    }
}
