package edu.diego.vaadin;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
//import com.vaadin.incubator.pagingcomponent.data.AbstractRowContainer;

import java.util.ArrayList;
import java.util.List;

/**
 * Heavily inspired by com.vaadin.incubator.pagingcomponent.demo.HiddenPagingContainer
 * See http://vaadin.com/forum/-/message_boards/message/126619
 * @author Mattias Jiderhamn
 */
public abstract class AbstractPagingContainer<T>  extends AbstractRowContainer {

/** Avoid serialization issues */
private static final long serialVersionUID = 1L;

  public static final int DEFAULT_PAGE_SIZE = 100;

  private final int pageSize;

  private List<T> currentPage = new ArrayList<>();

  private int currentPageNumber = -1;

  public AbstractPagingContainer() {
    this(DEFAULT_PAGE_SIZE);
  }

  public AbstractPagingContainer(int pageSize) {
    this.pageSize = pageSize;
  }

  public void init() { // TODO: Verify initialized
    // Query no of rows
    setNumberOfRows(calculateNumberOfRows());
    goToPage(1);
  }

  public Item getItem(Object o) {
    Integer rowNumber = (Integer) o;
    int pageNumber = 1 + (rowNumber / pageSize);

    if (pageNumber != currentPageNumber) {
      goToPage(pageNumber);
    }

    int currentPageIndex = rowNumber - ((currentPageNumber - 1) * pageSize);
    return new BeanItem<T>(currentPage.get(currentPageIndex));
  }

  protected void goToPage(int pageNumber) {
    // System.err.println("AbstractPagingContainer.getItem : Request row = " + rowNumber + ", loading page " + pageNumber);
    currentPage = loadPage(pageNumber, pageSize);
    currentPageNumber = pageNumber;
  }

  public void fireItemSetChanged() {
    super.fireItemSetChanged();
  }

  protected abstract int calculateNumberOfRows();

  protected abstract List<T> loadPage(int pageNumber, int pageSize);
}