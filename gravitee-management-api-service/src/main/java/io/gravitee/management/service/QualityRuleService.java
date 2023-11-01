/**
 * Copyright (C) 2015 The Gravitee team (http://gravitee.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gravitee.management.service;

import io.gravitee.management.model.quality.NewQualityRuleEntity;
import io.gravitee.management.model.quality.QualityRuleEntity;
import io.gravitee.management.model.quality.UpdateQualityRuleEntity;

import java.util.List;

/**
 * @author Azize ELAMRANI (azize.elamrani at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface QualityRuleService {
    QualityRuleEntity create(NewQualityRuleEntity newEntity);
    void delete(String id);
    QualityRuleEntity update(UpdateQualityRuleEntity updateEntity);
    QualityRuleEntity findById(String id);
    List<QualityRuleEntity> findAll();
}
