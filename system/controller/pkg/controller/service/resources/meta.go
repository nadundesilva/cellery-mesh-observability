/*
 * Copyright (c) 2018 WSO2 Inc. (http:www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http:www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package resources

import (
	"github.com/wso2/product-vick/system/controller/pkg/apis/vick"
	"github.com/wso2/product-vick/system/controller/pkg/apis/vick/v1alpha1"
	metav1 "k8s.io/apimachinery/pkg/apis/meta/v1"
)

func createLabels(service *v1alpha1.Service) map[string]string {
	labels := make(map[string]string, len(service.ObjectMeta.Labels)+3)

	labels[vick.ServiceNameLabelKey] = service.Name
	labels[vick.CellNameLabelKey] = service.Spec.Cell
	labels[vick.CellServiceTypeLabelKey] = CellServiceTypeService
	// order matters
	// todo: update the code if override is not possible
	for k, v := range service.ObjectMeta.Labels {
		labels[k] = v
	}
	return labels
}

func createSelector(service *v1alpha1.Service) *metav1.LabelSelector {
	return &metav1.LabelSelector{MatchLabels: createLabels(service)}
}


func deploymentName(service *v1alpha1.Service) string {
	return service.Name + "-deployment"
}


func k8sServiceName(service *v1alpha1.Service) string {
	return service.Name + "-service"
}