package com.example.gymfitness.adapters.home;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gymfitness.R;
import com.example.gymfitness.adapters.WorkoutAdapter;
import com.example.gymfitness.data.entities.Workout;
import com.example.gymfitness.databinding.RecommandRvcItemBinding;

import java.util.ArrayList;

public class RecommendExRCVApdater extends RecyclerView.Adapter<RecommendExRCVApdater.WorkoutRCMViewHolder> {
    ArrayList<Workout> listWorkout = new ArrayList<>();

    public RecommendExRCVApdater(ArrayList<Workout> list){
        this.listWorkout = list;
    }
    private OnWorkoutRCMListener listener;

    public interface OnWorkoutRCMListener {
        void onItemClick(Workout workout);
    }

    public void setOnItemClickListener(OnWorkoutRCMListener listener) {
        this.listener = listener;
    }
    @NonNull
    @Override
    public RecommendExRCVApdater.WorkoutRCMViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecommandRvcItemBinding binding = DataBindingUtil.inflate(layoutInflater,R.layout.recommand_rvc_item, parent, false);
        return new RecommendExRCVApdater.WorkoutRCMViewHolder(binding);
    }



    static class WorkoutRCMViewHolder extends RecyclerView.ViewHolder {
        private RecommandRvcItemBinding binding;
        public WorkoutRCMViewHolder(@NonNull RecommandRvcItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(Workout workout, OnWorkoutRCMListener listener) {
            binding.setItem(workout);
            binding.executePendingBindings();

            Glide.with(binding.thumbnail.getContext())
                    .load(workout.getThumbnail())
                    .placeholder(R.drawable.woman_helping_man_gym)
                    .error(R.drawable.woman_helping_man_gym)
                    .into(binding.thumbnail);

            if (listener != null) {
                itemView.setOnClickListener(v -> listener.onItemClick(workout));
            }
        }
    }

    @NonNull
    @Override
    public void onBindViewHolder(@NonNull WorkoutRCMViewHolder holder, int position) {
        Workout workout = listWorkout.get(position);
        holder.bind(workout,listener);
    }

    @Override
    public int getItemCount() {
        return listWorkout == null ? 0 : listWorkout.size();
    }


    public void setWorkoutList(ArrayList<Workout> workouts) {
        this.listWorkout.clear();
        this.listWorkout.addAll(workouts);
        notifyDataSetChanged();
    }
}
