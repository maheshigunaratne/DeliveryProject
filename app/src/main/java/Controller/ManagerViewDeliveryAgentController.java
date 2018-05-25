package Controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hp.deliveryproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Model.DeliveryDetails;
import Model.User;



public class ManagerViewDeliveryAgentController extends RecyclerView.Adapter<ManagerViewDeliveryAgentController.MyViewHolder> {

    private List<User> managerViewDeliveryAgentList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, phoneNumber, email;

        public MyViewHolder(View view){
            super(view);
            name = (TextView) view.findViewById((R.id.managerViewDeliveryAgentName));
            phoneNumber = (TextView) view.findViewById((R.id.managerViewDeliveryAgentPhoneNumber));
            email = (TextView) view.findViewById((R.id.managerViewDeliveryAgentEmail));
        }
    }

    public ManagerViewDeliveryAgentController(List<User> managerViewDeliveryAgentList){
        this.managerViewDeliveryAgentList = managerViewDeliveryAgentList;
    }

    @Override
    public ManagerViewDeliveryAgentController.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.managerviewdeliveryagent_row, parent, false);
        return new ManagerViewDeliveryAgentController.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        User managerViewDeliveryAgent = managerViewDeliveryAgentList.get(position);
        holder.name.setText(managerViewDeliveryAgent.getName());
        holder.phoneNumber.setText(managerViewDeliveryAgent.getPhoneNumber());
        holder.email.setText(managerViewDeliveryAgent.getEmail());
    }

    @Override
    public int getItemCount() {
        return managerViewDeliveryAgentList.size();
    }

}
