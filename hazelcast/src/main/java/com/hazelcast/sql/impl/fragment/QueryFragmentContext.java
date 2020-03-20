/*
 * Copyright (c) 2008-2020, Hazelcast, Inc. All Rights Reserved.
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

package com.hazelcast.sql.impl.fragment;

import com.hazelcast.sql.impl.expression.ExpressionEvalContext;
import com.hazelcast.sql.impl.state.QueryStateCancellationToken;
import com.hazelcast.sql.impl.worker.QueryFragmentScheduleCallback;

import java.util.List;

/**
 * Context of a running query fragment.
 */
public final class QueryFragmentContext implements ExpressionEvalContext {

    private final List<Object> arguments;
    private final QueryFragmentScheduleCallback scheduleCallback;
    private final QueryStateCancellationToken cancellationToken;

    public QueryFragmentContext(
        List<Object> arguments,
        QueryFragmentScheduleCallback scheduleCallback,
        QueryStateCancellationToken cancellationToken
    ) {
        assert arguments != null;

        this.cancellationToken = cancellationToken;
        this.arguments = arguments;
        this.scheduleCallback = scheduleCallback;
    }

    @Override
    public List<Object> getArguments() {
        return arguments;
    }

    public void schedule() {
        scheduleCallback.schedule();
    }

    public void checkCancelled() {
        cancellationToken.checkCancelled();
    }
}
