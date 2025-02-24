/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Other licenses:
 * -----------------------------------------------------------------------------
 * Commercial licenses for this work are available. These replace the above
 * ASL 2.0 and offer limited warranties, support, maintenance, and commercial
 * database integrations.
 *
 * For more information, please visit: http://www.jooq.org/licenses
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
package org.jooq.impl;

import static org.jooq.impl.Keywords.F_CEIL;
import static org.jooq.impl.Keywords.F_CEILING;

import org.jooq.Context;
import org.jooq.Field;

/**
 * @author Lukas Eder
 */
final class Ceil<T extends Number> extends AbstractField<T> {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = -7273879239726265322L;

    private final Field<T>    argument;

    Ceil(Field<T> argument) {
        super(DSL.name("ceil"), argument.getDataType());

        this.argument = argument;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {







            // [#8275] Improved emulation for SQLite
            case SQLITE:
                Field<Long> cast = DSL.cast(argument, SQLDataType.BIGINT);
                ctx.sql('(').visit(cast).sql(" + (").visit(argument).sql(" > ").visit(cast).sql("))");
                break;






            case H2:
                ctx.visit(F_CEILING).sql('(').visit(argument).sql(')');
                break;

            default:
                ctx.visit(F_CEIL).sql('(').visit(argument).sql(')');
                break;
        }
    }
}
