package com.example.newdiplwm;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class StatisticAdapter extends RecyclerView.Adapter<StatisticAdapter.StatisticHolder> {


    private List<UserGameStats> statistics = new ArrayList<>();
    private static final List<Integer> images = new ArrayList<Integer>();
    Context context;

    private void initList(){
        images.add(R.drawable.rock_scissor_paper);
        images.add(R.drawable.math_game);
        images.add(R.drawable.memory_game);
        images.add(R.drawable.select_object);
        images.add(R.drawable.order_game);
        images.add(R.drawable.suitcase_game);
        images.add(R.drawable.shadow_game);
        images.add(R.drawable.clothes_game);
        images.add(R.drawable.scissors);
        images.add(R.drawable.scissors);
        images.add(R.drawable.scissors);
        images.add(R.drawable.scissors);
    }


    @NonNull
    @Override
    public StatisticHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.statistic_item, viewGroup, false);
        return new StatisticHolder(itemView);
    }



    @Override
    public void onBindViewHolder(@NonNull final StatisticHolder gameHolder, int i) {
        UserGameStats currentStatistic = statistics.get(i);

        gameHolder.imageView.setImageResource(images.get(i));
        gameHolder.textViewTitle.setText(String.valueOf(GameHelper.getGreekName(currentStatistic.name)));
        gameHolder.textView.setText(String.valueOf(currentStatistic.statistic.getHit()));
        gameHolder.textView1.setText(String.valueOf(currentStatistic.statistic.getMiss()));

//        if (currentStatistic.statistic.getPlayTotalTime()<60)
//        {
//            gameHolder.textView3.setText(String.format("%.1f",(currentStatistic.statistic.getPlayTotalTime())) + " (secs)");
//        }
       // else
        //{
            gameHolder.textView3.setText(String.format("%.1f",((currentStatistic.statistic.getPlayTotalTime()%3600)/60)) + " (mins)");
        //}
        gameHolder.textView2.setText(String.valueOf(currentStatistic.statistic.getScore()));
        gameHolder.textView4.setText(String.valueOf(currentStatistic.statistic.getDays()));
        gameHolder.textView5.setText(String.format("%.1f", (currentStatistic.statistic.getAccuracy()*100))+"%");
        gameHolder.textView6.setText(String.valueOf(currentStatistic.statistic.getQuits()));


        gameHolder.setOnClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                Pair<View, String> p1 = Pair.create((View) gameHolder.imageView, "test");
                Pair<View, String> p2 = Pair.create((View) gameHolder.textViewTitle, "Stringaki");

                if (images.size() <=0)
                {
                    initList();
                }


                ActivityOptionsCompat activityOptionsCompat = (ActivityOptionsCompat) ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,p1,p2);
                Intent intent = new Intent(context, Charts.class);
                intent.putExtra("image", GameHelper.getGreekName(statistics.get(position).name));
                intent.putExtra("gameId", statistics.get(position).statistic.getGameIdForeign());
                intent.putExtra("lel",images.get(position));
//                intent.putExtra(HIT,statistics.get(position).statistic.getHit());
//                intent.putExtra(MISS,statistics.get(position).statistic.getMiss());
//                intent.putExtra(PLAYTOTALTIME,statistics.get(position).statistic.getPlayTotalTime());
//                intent.putExtra(SCORE,statistics.get(position).statistic.getScore());
                context.startActivity(intent, activityOptionsCompat.toBundle());
//                Intent intent=new Intent(context,Charts.class);
//                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return statistics.size();
    }

    public void setStatistics(List<UserGameStats> statistics, Context context) {
        this.statistics = statistics;
        this.context = context;

        notifyDataSetChanged();
        initList();
    }

    class StatisticHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;
        private TextView textView;
        private TextView textView1;
        private TextView textView2;
        private TextView textView3;
        private TextView textView4;
        private TextView textView5;
        private TextView textView6;
        private TextView textViewTitle;

        private OnItemClickListener onItemClickListener;

        public StatisticHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewC);
            textView = itemView.findViewById(R.id.tbl_txt1_value);
            textView1 = itemView.findViewById(R.id.tbl_txt2_value);
            textView2 = itemView.findViewById(R.id.tbl_txt3_value);
            textView3 = itemView.findViewById(R.id.tbl_txt4_value);
            textView4 = itemView.findViewById(R.id.tbl_txt5_value);
            textView5 = itemView.findViewById(R.id.tbl_txt6_value);
            textView6 = itemView.findViewById(R.id.tbl_txt7_value);
            textViewTitle = itemView.findViewById(R.id.tbl_Title);

            itemView.setOnClickListener(this);


//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position = getAdapterPosition();
//                    if (onItemClickListener != null && position != RecyclerView.NO_POSITION) {
//                        onItemClickListener.onItemClick(statistics.get(position));
//                    }
//                }
//            });

        }

        @Override
        public void onClick(View view) {

            this.onItemClickListener.onItemClick(view, getLayoutPosition());
        }

        public void setOnClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;

        }
    }


    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

}