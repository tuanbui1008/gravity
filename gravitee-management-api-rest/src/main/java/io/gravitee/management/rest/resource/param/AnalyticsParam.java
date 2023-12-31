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
package io.gravitee.management.rest.resource.param;

import io.swagger.annotations.ApiParam;

import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public class AnalyticsParam {

    @QueryParam("from")
    @ApiParam(value = "Timestamp used to define the start date of the time window to query")
    private long from;

    @QueryParam("to")
    @ApiParam(value = "Timestamp used to define the end date of the time window to query")
    private long to;

    @QueryParam("interval")
    @ApiParam(
            value = "The time interval when getting histogram data (in milliseconds)",
            example = "600000"
    )
    private long interval;

    @QueryParam("query")
    @ApiParam(
            value = "The Lucene query used to filter data",
            example = "api:xxxx-xxxx-xxxx-xxxx AND plan:yyyy-yyyy-yyyy-yyyy AND host:\"demo.gravitee.io\" AND path:/test"
    )
    private String query;

    @QueryParam("field")
    @ApiParam(value = "The field to query when doing `group_by` queries")
    private String field;

    @QueryParam("size")
    @ApiParam(value = "The number of data to retrieve")
    private int size;

    @QueryParam("type")
    @ApiParam(
            value = "The type of data to retrieve",
            required = true,
            allowableValues = "group_by,date_histo,count"
    )
    private AnalyticsTypeParam type;

    @QueryParam("ranges")
    @ApiParam(
            value = "Ranges allows you to group field's data. Mainly used to group HTTP statuses code with `group_by` queries",
            example = "100:199;200:299;300:399;400:499;500:599"
    )
    private RangesParam ranges;

    @QueryParam("aggs")
    @ApiParam(
            value = "Aggregations are used when doing `date_histo` queries and allows you to group field's data. Mainly used to group HTTP statuses code",
            example = "field:status or avg:response-time;avg:api-response-time"
    )
    private AggregationsParam aggs;

    @QueryParam("order")
    @ApiParam(
            value = "The field used to sort results. Can be asc or desc (prefix with minus '-') ",
            example = "order:-response-time"
    )
    private OrderParam order;

    public long getFrom() {
        return from;
    }

    public void setFrom(long from) {
        this.from = from;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public long getTo() {
        return to;
    }

    public void setTo(long to) {
        this.to = to;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public AnalyticsTypeParam getTypeParam() {
        return type;
    }

    public void setTypeParam(AnalyticsTypeParam type) {
        this.type = type;
    }

    public AnalyticsTypeParam.AnalyticsType getType() {
        return type.getValue();
    }

    public List<Range> getRanges() {
        return (ranges == null) ? null : ranges.getValue();
    }

    public List<Aggregation> getAggregations() {
        return (aggs == null) ? null : aggs.getValue();
    }

    public OrderParam.Order getOrder() {
        return (order == null) ? null : order.getValue();
    }

    public void validate() throws WebApplicationException {
        if (type.getValue() == null) {
            throw new WebApplicationException(Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("Query parameter 'type' is not valid")
                    .build());
        }

        if (from == -1) {
            throw new WebApplicationException(Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("Query parameter 'from' is not valid")
                    .build());
        }

        if (to == -1) {
            throw new WebApplicationException(Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("Query parameter 'to' is not valid")
                    .build());
        }

        if (interval == -1) {
            throw new WebApplicationException(Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("Query parameter 'interval' is not valid")
                    .build());
        }

        if (interval < 1_000 || interval > 1_000_000_000) {
            throw new WebApplicationException(Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("Query parameter 'interval' is not valid. 'interval' must be >= 1000 and <= 1000000000")
                    .build());
        }

        if (from >= to) {
            throw new WebApplicationException(Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("'from' query parameter value must be greater than 'to'")
                    .build());
        }

        if (type.getValue() == AnalyticsTypeParam.AnalyticsType.GROUP_BY) {
            // we need a field and, optionally, a list of ranges
            if (field == null || field.trim().isEmpty()) {
                throw new WebApplicationException(Response
                        .status(Response.Status.BAD_REQUEST)
                        .entity("'field' query parameter is required for 'group_by' request")
                        .build());
            }
        }
    }
}
