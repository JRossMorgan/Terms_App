package com.termsapp.Pages;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.termsapp.R;

import java.util.List;

import entites.AssessmentClass;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {
    public List<AssessmentClass> assessmentClassList;
    private final Context context;
    private final LayoutInflater assessmentInflator;
    public AssessmentAdapter(Context context){
        assessmentInflator = LayoutInflater.from(context);
        this.context = context;
    }
    public class AssessmentViewHolder extends RecyclerView.ViewHolder{
        private final TextView assessmentViewItem;

        public AssessmentViewHolder(@NonNull View itemView) {
            super(itemView);
            assessmentViewItem = itemView.findViewById(R.id.assessmentView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final AssessmentClass current = assessmentClassList.get(position);
                    Intent intent = new Intent(context, AddAssessments.class);
                    intent.putExtra("Assessment ID", current.getAssessmentId());
                    intent.putExtra("Title", current.getTitle());
                    intent.putExtra("Type", current.getType());
                    intent.putExtra("End Date", current.formattedEnd(current.getEndDate()));
                    intent.putExtra("Course ID", current.getCourseId());
                    context.startActivity(intent);
                }
            });
        }
    }
    @NonNull
    @Override
    public AssessmentAdapter.AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = assessmentInflator.inflate(R.layout.assessment_item, parent, false);
        return new AssessmentViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.AssessmentViewHolder holder, int position) {
        if(assessmentClassList != null){
            AssessmentClass current = assessmentClassList.get(position);
            String name = current.getTitle();
            holder.assessmentViewItem.setText(name);
        }
        else {
            holder.assessmentViewItem.setText("None Found");
        }
    }

    @Override
    public int getItemCount() {
       if (assessmentClassList != null){
           return assessmentClassList.size();
       }
       else {
           return 0;
       }
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setAssessmentClassList(List<AssessmentClass> assessments){
        assessmentClassList = assessments;
        notifyDataSetChanged();
    }
}
