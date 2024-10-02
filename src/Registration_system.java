public abstract class Registration_system<T> {
    // Abstract methods to be implemented by child classes
    //T is to include generic programming (im abt to cry ive lost all my brain cells)
    public abstract void view();
    public abstract void add(T item);
    public abstract void delete(T item);
}

