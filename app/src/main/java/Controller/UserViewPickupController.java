package Controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.deliveryproject.ManagerAssignDeliveryAgent;
import com.example.hp.deliveryproject.ManagerViewDeliveryAgent;
import com.example.hp.deliveryproject.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import Model.DeliveryDetails;


public class UserViewPickupController extends RecyclerView.Adapter<UserViewPickupController.MyViewHolder>{

    private List<DeliveryDetails> deliveryDetailsList;
    Context contt;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView date, fromLocation, toLocation, status;



        public MyViewHolder(View view){
            super(view);
            contt=view.getContext();
            date = (TextView) view.findViewById((R.id.userReqestRowDate));
            fromLocation = (TextView) view.findViewById((R.id.userRequestRowFromLocation));
            toLocation = (TextView) view.findViewById((R.id.userRequestRowToLocation));
            status = (TextView) view.findViewById(R.id.userRequestRowStatus);

        }
    }

    public UserViewPickupController(List<DeliveryDetails> deliveryDetailsList){
        this.deliveryDetailsList = deliveryDetailsList;
    }

    @Override
    public UserViewPickupController.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.userrequestrow, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final UserViewPickupController.MyViewHolder holder, int position) {

        final DeliveryDetails deliveryDetails = deliveryDetailsList.get(position);
        holder.date.setText(deliveryDetails.getDeliveryDate());
        holder.fromLocation.setText(deliveryDetails.getFromLocation());
        holder.toLocation.setText(deliveryDetails.getToLocation());
        holder.status.setText(deliveryDetails.getStatus());
    }

    @Override
    public int getItemCount() {
        return deliveryDetailsList.size();
    }

}
