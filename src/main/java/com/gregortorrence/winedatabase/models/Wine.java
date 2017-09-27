package com.gregor.assessment.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Created by Gregor Torrence on 6/9/17.
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Accessors(chain = true)
public class Wine {
    private String  uuid;
    private String  name;
    private String  winery;
    private String  varietal;
    private Integer vintage;
    private String  appellation;
}
