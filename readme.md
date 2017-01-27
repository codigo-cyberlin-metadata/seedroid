Android App
===================

BaseRecyclerActivity

How to use
extending BaseRecyclerActivity to your class of activity
implement two method which is onInitAdapter and onLoadItems on your class
- onInitAdapter : for define your item adapter
- onLoadItems   : for handle when get result data

public class SampleActivity extend BaseRecyclerActivity {
    public BaseRecyclerAdapter onInitAdapter() {
        return SampleAdapter();
    }

    public void onLoadItems(int limit, int offset) {
        // your logic here

        boolean status = true;
        String message = "success";
        ArrayList<SampleObject> data = getData();

        customListView.bindItems(status, message, data);
    }
}

BasePagerActivity

How to use
extending BasePagerActivity to your class of activity
implement two method which is onInitItems on your class
- onInitItems : for define fragment to load

public class SampleActivity extend BaseRecyclerActivity {
    public onInitItems() {
        // your logic here

        items.add(new PagerChildModel(Sample1Fragment.newInstance(), titles[0]));
        items.add(new PagerChildModel(Sample2Fragment.newInstance(), titles[1]));
        items.add(new PagerChildModel(Sample3Fragment.newInstance(), titles[2]));
    }
}



