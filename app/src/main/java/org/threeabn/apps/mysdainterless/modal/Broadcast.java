package org.threeabn.apps.mysdainterless.modal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by k-joseph on 26/09/17.
 *
 * @TODO TV or radio
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Broadcast extends MySDAObject {
}
