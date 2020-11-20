package com.codetrex.cayroshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codetrex.cayroshop.R;
import com.codetrex.cayroshop.model.ClientCard;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ClientCardApater extends RecyclerView.Adapter<ClientCardApater.ViewHolder> {
    private ArrayList<ClientCard>clientCards;
    private Context context;

    public ClientCardApater(ArrayList<ClientCard> clientCards, Context context) {
        this.clientCards = clientCards;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.clientcard_item,parent,false);
        return new ClientCardApater.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
          holder.cardNo.setTextContent(clientCards.get(position).getCreditCardNoShort());
    }

    @Override
    public int getItemCount() {
        return clientCards.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EditText enterCvv;
        RadioButton selectCardRadiobtn;
        Text cardBankeName,cardNo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            enterCvv = itemView.findViewById(R.id.enter_cvv);
            selectCardRadiobtn = itemView.findViewById(R.id.radio);
            cardNo = itemView.findViewById(R.id.card_no);
        }
    }
}
