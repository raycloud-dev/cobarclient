/**
 * Copyright 1999-2011 Alibaba Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.cobarclient;

import org.springframework.dao.DataAccessException;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * A DAO base class definition which adds more helper methods on batch operations.<br> Users can configure their DAO
 * implementations with same configuration items of {@link MysdalSqlMapClientTemplate}.<br> <br> Feature requested by
 * Wangyi
 *
 * @author fujohnwang, update by Wangyi
 * @since 1.0
 */
public class MysdalCobarSqlMapClientDaoSupport extends BaseSqlMapClientDaoSupport {
    public int batchInsert(final String statementName, final List<?> entities)
            throws DataAccessException {
        if (CollectionUtils.isEmpty(entities)) {
            return 0;
        }
        if (isPartitionBehaviorEnabled()) {
            MysdalSqlMapClientTemplate template = ((MysdalSqlMapClientTemplate) getSqlMapClientTemplate());
            if (template.isHasShard(statementName, entities.get(0))) {
                return template.batchInsert(statementName, entities);
            } else {
                return super.batchInsert(statementName, entities);
            }
        } else {
            return super.batchInsert(statementName, entities);
        }
    }

    public int batchDelete(final String statementName, final List<?> entities)
            throws DataAccessException {
        if (CollectionUtils.isEmpty(entities)) {
            return 0;
        }
        if (isPartitionBehaviorEnabled()) {
            MysdalSqlMapClientTemplate template = ((MysdalSqlMapClientTemplate) getSqlMapClientTemplate());
            if (template.isHasShard(statementName, entities.get(0))) {
                return template.batchDelete(statementName, entities);
            } else {
                return super.batchDelete(statementName, entities);
            }
        } else {
            return super.batchDelete(statementName, entities);
        }
    }

    public int batchUpdate(final String statementName, final List<?> entities)
            throws DataAccessException {
        if (CollectionUtils.isEmpty(entities)) {
            return 0;
        }
        if (isPartitionBehaviorEnabled()) {
            MysdalSqlMapClientTemplate template = ((MysdalSqlMapClientTemplate) getSqlMapClientTemplate());
            if (template.isHasShard(statementName, entities.get(0))) {
                return template.batchUpdate(statementName, entities);
            } else {
                return super.batchUpdate(statementName, entities);
            }
        } else {
            return super.batchUpdate(statementName, entities);
        }
    }

    protected boolean isPartitionBehaviorEnabled() {
        if (getSqlMapClientTemplate() instanceof MysdalSqlMapClientTemplate) {
            return true;
        }
        return false;
    }
}
