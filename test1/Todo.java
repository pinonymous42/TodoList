public class Todo {
    private int index_;
    private String title_;
    private String contents_;
    private String created_;
    private String modified_;
    private String deadline_;
    private String priority_;
    private String createdBy_;
    private String editedBy_;
    private int archive_;

    Todo()
    {
        this.index_ = 0;
        this.title_ = null;
        this.contents_ = null;
        this.created_ = null;
        this.modified_ = null;
        this.deadline_ = null;
        this.priority_ = null;
        this.createdBy_ = null;
        this.editedBy_ = null;
        this.archive_ = 0;
    }

    Todo(int index, String title, String contents, String created, String modified, String deadline, String priority, String createdBy, String editedBy, int archive)
    {
        this.index_ = index;
        this.title_ = title;
        this.contents_ = contents;
        this.created_ = created;
        this.modified_ = modified;
        this.deadline_ = deadline;
        this.priority_ = priority;
        this.createdBy_ = createdBy;
        this.editedBy_ = editedBy;
        this.archive_ = archive;
    }

    public int getIndex()
    {
        return (index_);
    }

    public String getTitle()
    {
        return (title_);
    }

    public String getContents()
    {
        return (contents_);
    }

    public String getCreated()
    {
        return (created_);
    }

    public String getModified()
    {
        return (modified_);
    }

    public String getDeadline()
    {
        return (deadline_);
    }

    public String getPriority()
    {
        return (priority_);
    }

    public String getCreatedBy()
    {
        return (createdBy_);
    }

    public String getEditedBy()
    {
        return (editedBy_);
    }

    public int getArchive()
    {
        return (archive_);
    }

    public void setIndex(int index)
    {
        this.index_ = index;
    }

    public void setTitle(String title)
    {
        this.title_ = title;
    }

    public void setContents(String contents)
    {
        this.contents_ = contents;
    }

    public void setCreated(String created)
    {
        this.created_ = created;
    }

    public void setModified(String modified)
    {
        this.modified_ = modified;
    }

    public void setDeadline(String deadline)
    {
        this.deadline_ = deadline;
    }

    public void setPriority(String priority)
    {
        this.priority_ = priority;
    }

    public void setCreatedBy(String createdBy)
    {
        this.createdBy_ = createdBy;
    }

    public void setEditedBy(String editedBy)
    {
        this.editedBy_ = editedBy;
    }

    public void setArchive(int archive)
    {
        this.archive_ = archive;
    }
}
