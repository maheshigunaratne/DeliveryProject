package Controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.hp.deliveryproject.ManagerAssignDeliveryAgent;
import com.example.hp.deliveryproject.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import Model.DeliveryDetails;



public class OrdersListController extends RecyclerView.Adapter<OrdersListController.MyViewHolder>{

    private List<DeliveryDetails> managerViewPickupList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView date, fromLocation, toLocation, status;
        public ImageButton btnAssign;

        public MyViewHolder(View view){
            super(view);
            date = (TextView) view.findViewById((R.id.userReqestRowDate1));
            fromLocation = (TextView) view.findViewById((R.id.userRequestRowFromLocation1));
            toLocation = (TextView) view.findViewById((R.id.userRequestRowToLocation1));
            status = (TextView) view.findViewById(R.id.userRequestRowStatus1);
            btnAssign= (ImageButton) view.findViewById(R.id.btnassign);
        }
    }

    public OrdersListController(List<DeliveryDetails> managerViewPickupList){
        this.managerViewPickupList = managerViewPickupList;
    }

    @Override
    public OrdersListController.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_layout, parent, false);
        context = parent.getContext();
        return new OrdersListController.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OrdersListController.MyViewHolder holder, int position) {
        final DeliveryDetails deliveryDetails = managerViewPickupList.get(position);
        holder.date.setText(deliveryDetails.getDeliveryDate());
        holder.fromLocation.setText(deliveryDetails.getFromLocation());
        holder.toLocation.setText(deliveryDetails.getToLocation());
        holder.status.setText(deliveryDetails.getStatus());
        holder.btnAssign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    //assignDelAgent(Integer.parseInt(deliveryDetails.getDeliveryID()));
                Intent intent = new Intent(v.getContext(),ManagerAssignDeliveryAgent.class);
                //Create the bundle
                Bundle bundle = new Bundle();

                //Add your data to bundle
                bundle.putString("deliveryID", deliveryDetails.getDeliveryID());

                //Add the bundle to the intent
                intent.putExtras(bundle);
                //assignDelAgent(Integer.parseInt(deliveryDetails.getDeliveryID()));
                context.startActivity(intent);
            }
        });

    }

//    private void assignDelAgent(int DelId)
//    {
//
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("tables");
//        myRef.child("deliverydetails").child(""+DelId).child("status").setValue("assigned");
//        System.out.println();
//    }

    @Override
    public int getItemCount() {
        return managerViewPickupList.size();
    }
}
