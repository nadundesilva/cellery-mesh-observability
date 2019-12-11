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

package io.cellery.observability.auth;

import io.cellery.observability.auth.internal.ServiceHolder;
import org.powermock.reflect.Whitebox;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.wso2.carbon.config.provider.ConfigProvider;
import org.wso2.carbon.config.provider.ConfigProviderImpl;
import org.wso2.carbon.config.reader.ConfigFileReader;
import org.wso2.carbon.config.reader.YAMLBasedConfigFileReader;
import org.wso2.carbon.secvault.SecureVault;
import org.wso2.carbon.secvault.internal.SecureVaultImpl;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Test Cases for Cellery Config.
 */
public class AuthConfigTestCase {

    @BeforeMethod
    public void init() {
        String carbonHomePath = this.getClass().getResource("/").getFile();
        Path configPath = Paths.get(carbonHomePath, "conf", "deployment.yaml");
        ConfigFileReader configFileReader = new YAMLBasedConfigFileReader(configPath);
        SecureVault secureVault = new SecureVaultImpl();
        ConfigProvider configProvider = new ConfigProviderImpl(configFileReader, secureVault);
        ServiceHolder.setConfigProvider(configProvider);
    }

    @AfterMethod
    public void cleanUp() {
        ServiceHolder.setConfigProvider(null);
    }

    @Test
    public void testLoadConfig() throws Exception {
        Whitebox.setInternalState(AuthConfig.class, "authConfig", (Object) null);
        AuthConfig authConfig = AuthConfig.getInstance();
        Assert.assertNotNull(authConfig);
        Assert.assertEquals("https://idp.cellery-system", authConfig.getIdpUrl());
        Assert.assertEquals("http://cellery-dashboard", authConfig.getCallbackUrl());
        Assert.assertEquals("testadmin", authConfig.getIdpUsername());
        Assert.assertEquals("testpass", authConfig.getIdpPassword());
    }

    @Test
    public void testAccessLoadedConfig() throws Exception {
        Whitebox.setInternalState(AuthConfig.class, "authConfig", (Object) null);
        AuthConfig initialAuthConfig = AuthConfig.getInstance();
        Assert.assertSame(AuthConfig.getInstance(), initialAuthConfig);
    }
}