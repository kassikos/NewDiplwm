package com.example.newdiplwm;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameHolder> {
    private List<Game> games = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    @NonNull
    @Override
    public GameHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.game_item,viewGroup,false);
        return new GameHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GameHolder gameHolder, int i) {
        Game currentGame = games.get(i);
        if (currentGame.getName().equals("Rock"))
        {
            gameHolder.imageView.setImageResource(R.drawable.stone);
            gameHolder.textView.setText(R.string.Rock);

        }
        else if(currentGame.getName().equals("Calcution"))
        {
            gameHolder.imageView.setImageResource(R.drawable.numeric);
            gameHolder.textView.setText(R.string.Calcution);
        }
        else if (currentGame.getName().equals("MemoryMatrix"))
        {
            gameHolder.imageView.setImageResource(R.drawable.scissors);
            gameHolder.textView.setText(R.string.MemoryMatrix);
        }
        else if (currentGame.getName().equals("ObjectSelector"))
        {
            gameHolder.imageView.setImageResource(R.drawable.scissors);
            gameHolder.textView.setText(R.string.ObjectSelector);
        }
        else if (currentGame.getName().equals("OrderGame"))
        {
            gameHolder.imageView.setImageResource(R.drawable.scissors);
            gameHolder.textView.setText(R.string.OrderGame);
        }
        else if (currentGame.getName().equals("Suitcase"))
        {
            gameHolder.imageView.setImageResource(R.drawable.scissors);
            gameHolder.textView.setText(R.string.Suitcase);
        }
        else if (currentGame.getName().equals("ShadowGame"))
        {
            gameHolder.imageView.setImageResource(R.drawable.scissors);
            gameHolder.textView.setText(R.string.ShadowGame);
        }
        else if (currentGame.getName().equals("PersonPickGame"))
        {
            gameHolder.imageView.setImageResource(R.drawable.scissors);
            gameHolder.textView.setText(R.string.PersonPickGame);
        }
        else if (currentGame.getName().equals("SoundWord"))
        {
            gameHolder.imageView.setImageResource(R.drawable.scissors);
            gameHolder.textView.setText(R.string.SoundWord);
        }
        else if (currentGame.getName().equals("SoundImage"))
        {
            gameHolder.imageView.setImageResource(R.drawable.scissors);
            gameHolder.textView.setText(R.string.SoundImage);
        }




        gameHolder.textView1.setText(currentGame.getDescription());
        // Bitmap bmp = BitmapFactory.decodeByteArray(currentGame.getImage(), 0, currentGame.getImage().length);



    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    public void setGames(List<Game> games)
    {
        this.games = games;
        notifyDataSetChanged();
    }

    class GameHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private TextView textView1;
        private ImageView imageView;

        public GameHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textViewTitle);
            textView1 = itemView.findViewById(R.id.textViewShortDesc);
            imageView = itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (onItemClickListener != null && position != RecyclerView.NO_POSITION) {
                        onItemClickListener.onItemClick(games.get(position));
                    }
                }
            });

        }
    }

    public interface OnItemClickListener{
        void onItemClick(Game game);
    }
    public void setOnClickListener (OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;

    }

}