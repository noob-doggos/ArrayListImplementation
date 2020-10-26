import java.util.Iterator;

/**
 * An implementation of MyList with an array (a longer exercise would be to
 * implement the List interface as is done in the class java.util.ArrayList: the
 * source of the ArrayList class is available from Sun. Check it out).
 */

public class MyArrayList<E> implements MyList<E>
{

    // Use an array for the implementation
    private E[] items;

    // Default capacity of the array
    private static final int DEFAULT_CAPACITY = 10;

    // Number of elements in the array
    private int size;

    /**
     * Constructs a MyArrayList with a specified capacity
     */
    @SuppressWarnings("unchecked")
    public MyArrayList(int initialCapacity)
    {
        this.items = (E[]) new Object[initialCapacity];
        this.size = 0;
    }

    /**
     * Constructs a MyArrayList with a default capacity
     */
    @SuppressWarnings("unchecked")
    public MyArrayList()
    {
        this.items = (E[]) new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    /**
     * Returns the number of elements in this list.
     */
    public int size()
    {
        return this.size;
    }

    /**
     * Returns true if this list contains no elements.
     */
    public boolean isEmpty()
    {
        return this.size == 0;
    }

    /**
     * Appends the specified element to the end of this list
     */
    @SuppressWarnings("unchecked")
    public boolean add(E o)
    {
        // If there is no room in the array items
        // Make room for the new element
        if (this.size + 1 > this.items.length)
        {
            E[] itemsExpanded = (E[]) new Object[this.items.length + 1];
            for (int i = 0; i < this.size; i++)
            {
                itemsExpanded[i] = this.items[i];
            }
            this.items = itemsExpanded;
        }

        // add the new element
        this.items[this.size] = o;
        this.size++;

        return true;
    }

    /**
     * Empties this List
     */
    @SuppressWarnings("unchecked")
    public void clear()
    {
        this.items = (E[]) new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    /**
     * Returns the element at the specified position in this list.
     */
    public E get(int index)
    {
        return this.items[index];
    }

    /**
     * Returns the index of the specified element (-1 if there is no match)
     */
    public int indexOf(Object o)
    {
        // If o is null (look for a null element in the array)
        if (o == null)
        {
            for (int i = 0; i < this.size; i++)
            {
                if (this.items[i] == null)
                {
                    return i;
                }
            }

        }
        else
        {
            // o is an object (use equals)
            for (int i = 0; i < this.size; i++)
            {
                if (this.items[i].equals(o))
                {
                    return i;
                }
            }
        }

        // If we get here, o is not in the list
        return -1;
    }

    /**
     * Returns true if this list contains the specified element.
     */
    public boolean contains(Object o)
    {
        // easy with indexOf
        return this.indexOf(o) != -1;
    }

    /**
     * Removes the element in the List at position index
     */
    @SuppressWarnings("unchecked")
    public boolean remove(int index)
    {
        if (index < 0 || index >= this.size)
        {
            return false;
        }
        // compact the array
        E[] arrayCompacted = (E[]) new Object[this.size - 1];

        int actualIndex = 0;

        for (int i = 0; i < this.size; i++)
        {
            if (i == index)
            {
                continue;
            }
            arrayCompacted[actualIndex] = this.items[i];
            actualIndex++;
        }
        
        this.size--;

        // let's gc do its work
        this.items = arrayCompacted;
        return true;
    }

    /**
     * Removes the element in the List at position index
     */
    public boolean remove(Object o)
    {
        // easy with indexOf and remove
        return this.remove(this.indexOf(o));
    }

    /**
     * Adds the specified object at the specified location
     */
    @SuppressWarnings("unchecked")
    public boolean add(int index, E o)
    {
        E[] itemsExpanded = (E[]) new Object[this.size + 1 > this.items.length ? this.items.length + 1
            : this.items.length];
        // one way: add at the end and then shift the elements around
        for (int i = 0; i < index; i++)
        {
            itemsExpanded[i] = this.items[i];
        }
        itemsExpanded[index] = o;
        for (int i = index; i < this.size; i++)
        {
            itemsExpanded[i + 1] = this.items[i];
        }

        this.items = itemsExpanded;
        this.size++;

        return true;
    }

    /**
     * Is this List equal to the specified object?
     */
    @SuppressWarnings("unchecked")
    public boolean equals(Object o)
    {
        if (o instanceof MyArrayList)
        {
            // o is an ArrayList
            MyArrayList<E> myAl = (MyArrayList<E>) o;

            // if the number of elements is not the same, this and o are not the
            // same
            if (this.size() != myAl.size())
            {
                return false;
            }

            // Check the elements one by one
            for (int i = 0; i < myAl.size(); i++)
            {
                if (!this.get(i).equals(myAl.get(i)))
                {
                    return false;
                }
            }
            // At this point, the lists are equal
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * An inner class to define the iterator
     */
    private class MyIterator implements Iterator<E>
    {
        private int index = 0;

        private MyArrayList<E> list;

        private int lastIndex = -1; // index of the object most recently visited

        // by next

        /**
         * Create an iterator for a MyArrayList
         */
        public MyIterator(MyArrayList<E> list)
        {
            this.list = list;
        }

        /**
         * Any element left in the list?
         */
        public boolean hasNext()
        {
            return this.index < this.list.size();
        }

        /**
         * Returns the current element in the list and move to the next element
         */
        public E next()
        {
            E owo = list.get(this.index);
            this.lastIndex = this.index;
            this.index++;
            return owo;
        }

        /**
         * Removes the last object returned by next
         */
        public void remove()
        {
            this.list.remove(this.lastIndex);
        }
    }

    /**
     * Returns an iterator over the elements in this list in proper sequence.
     * 
     * @return an iterator over the elements in this list in proper sequence.
     */
    public Iterator<E> iterator()
    {
        return new MyIterator(this);
    }
}
