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
  private Integer currentPage;
  private List<T> content;


  public static <S> PageModel<S> mapPage(PageModel<?> fromPage, List<S> toList) {
    return PageModel.<S>builder()
      .content(toList)
      .currentPageTotalItems(fromPage.getCurrentPageTotalItems())
      .currentPage(fromPage.getCurrentPage())
      .numberOfPages(fromPage.getNumberOfPages())
      .totalItems(fromPage.getTotalItems())
      .build();
  }
  
}
