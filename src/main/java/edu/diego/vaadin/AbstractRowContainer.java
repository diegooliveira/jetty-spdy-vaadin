package edu.diego.vaadin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;

public abstract class AbstractRowContainer implements Container, Container.Indexed, Container.ItemSetChangeNotifier {

  protected int numberOfRows = 0;
  final protected List<Integer> rowIds = new ArrayList<Integer>();
  final protected List<Integer> rowIdsUnomodifiable = Collections.unmodifiableList(rowIds);

  final protected List<Object> propertyIds = new ArrayList<Object>();
  final protected List<Object> propertyIdsUnModifiable = Collections.unmodifiableList(propertyIds);
  protected Map<Object, Class> propertyIdToType = new HashMap<Object, Class>();
  protected Map<Object, Object> propertyIdToDefault = new HashMap<Object, Object>();
  protected List<ItemSetChangeListener> itemSetChangeListeners = new LinkedList<ItemSetChangeListener>();
  /*-- Container Implementation----------------------------------------------------------------------------------------*/

  public Object addItem() throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }


  public Item addItem(Object o) throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }


  public boolean containsId(Object itemId) {
    if (itemId instanceof Integer) {
      Integer rowNumber = (Integer) itemId;
      return rowNumber >= 0 && rowNumber < numberOfRows;
    } else {
      return false;
    }
  }

  public int size() {
    return numberOfRows;
  }

  public Collection<?> getItemIds() {
    return rowIdsUnomodifiable;
  }

  public boolean addContainerProperty(Object propertyId, Class<?> type, Object defaultValue) throws UnsupportedOperationException {
    if (propertyIds.contains(propertyId)) {
      return false;
    }
    propertyIds.add(propertyId);
    propertyIdToType.put(propertyId, type);
    if (defaultValue != null) {
      propertyIdToDefault.put(propertyId, defaultValue);
    }
    return true;
  }

  public boolean removeContainerProperty(Object propertyId) throws UnsupportedOperationException {
    if (!propertyIds.contains(propertyId)) {
      return false;
    }
    propertyIds.remove(propertyId);
    propertyIdToType.remove(propertyId);
    propertyIdToDefault.remove(propertyId);
    return true;
  }

  public Collection<?> getContainerPropertyIds() {
    return propertyIdsUnModifiable;
  }

  public Class<?> getType(Object propertyId) {
    return propertyIdToType.get(propertyId);
  }

  public Property getContainerProperty(Object itemId, Object propertyId) {
    return getItem(itemId).getItemProperty(propertyId);
  }

  public int indexOfId(Object itemId) {
    if (itemId instanceof Integer) {
      return (Integer) itemId;
    }
    return -1;
  }

  /*-- Container.Indexed Implementation ------------------------------------------------------------------------------*/
  public Object addItemAt(int i) throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  public Item addItemAt(int i, Object o) throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  public Object addItemAfter(Object o) throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  public Item addItemAfter(Object o, Object o1) throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  public boolean removeAllItems() throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  public boolean removeItem(Object o) throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  public Object getIdByIndex(int index) {
    return index;
  }

  public Object firstItemId() {
    return 0;
  }

  public Object lastItemId() {
    return numberOfRows - 1;
  }


  public boolean isFirstId(Object o) {
    if (o instanceof Integer) {
      Integer rowNumber = (Integer) o;
      return rowNumber == 0;
    }
    return false;
  }

  public boolean isLastId(Object o) {
    if (o instanceof Integer) {
      Integer rowNumber = (Integer) o;
      return rowNumber == numberOfRows - 1;
    }
    return false;
  }

  public Object nextItemId(Object o) {
    if (o instanceof Integer) {
      Integer rowNumber = (Integer) o;
      if (rowNumber < numberOfRows - 1) {
        return rowNumber + 1;
      }
    }
    return null;
  }

  public Object prevItemId(Object o) {
    if (o instanceof Integer) {
      Integer rowNumber = (Integer) o;
      if (rowNumber > 0) {
        return rowNumber - 1;
      }
    }
    return null;
  }


  public void addListener(ItemSetChangeListener itemSetChangeListener) {
    itemSetChangeListeners.add(itemSetChangeListener);
  }

  public void removeListener(ItemSetChangeListener itemSetChangeListener) {
    itemSetChangeListeners.remove(itemSetChangeListener);
  }

  protected void fireItemSetChanged(){
    ItemSetChangeEvent itemSetEvent = new ItemSetChangeEvent() {
      public Container getContainer() {
        return AbstractRowContainer.this;
      }
    };

    for (ItemSetChangeListener listener : itemSetChangeListeners) {
      listener.containerItemSetChange(itemSetEvent);
    }
  }

  protected int getNumberOfRows() {
    return numberOfRows;
  }

  protected void setNumberOfRows(int numberOfRows) {
    this.numberOfRows = numberOfRows;
    fireItemSetChanged();
  }

  public void addContainerProperty(Object propertyId, Class type) {
    addContainerProperty(propertyId, type, null);
  }
}
