package io.pwii.model;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class PageModel<T> {

  private Integer numberOfPages;
  private Long totalItems;
  private Integer currentPageTotalItems;
  private List<T> content;
  
}
