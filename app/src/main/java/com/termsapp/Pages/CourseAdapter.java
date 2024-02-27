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

import entites.CourseClass;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    public List<CourseClass> courseClassList;
    private final Context context;
    private final LayoutInflater courseInflater;
    public CourseAdapter(Context context){
        courseInflater = LayoutInflater.from(context);
        this.context = context;
    }
    public class CourseViewHolder extends RecyclerView.ViewHolder{
        private final TextView courseViewItem;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            courseViewItem = itemView.findViewById(R.id.courseView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final CourseClass current = courseClassList.get(position);
                    Intent intent =new Intent(context, AddCourses.class);
                    intent.putExtra("Course ID", current.getCourseId());
                    intent.putExtra("Title", current.getTitle());
                    intent.putExtra("Start Date", current.formattedStart(current.getStart()));
                    intent.putExtra("End Date", current.formattedEnd(current.getEnd()));
                    intent.putExtra("Status", current.getStatus());
                    intent.putExtra("Instructor's Name", current.getInstructorName());
                    intent.putExtra("Instructor's Phone Number", current.getInstructorNumber());
                    intent.putExtra("Instructor's Email", current.getInstructorEmail());
                    intent.putExtra("Term ID", current.getTermId());
                    context.startActivity(intent);
                }
            });
        }
    }
    @NonNull
    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = courseInflater.inflate(R.layout.courses_item, parent, false);
        return new CourseViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        if(courseClassList != null){
            CourseClass current = courseClassList.get(position);
            String name = current.getTitle();
            holder.courseViewItem.setText(name);
        }
        else {
            holder.courseViewItem.setText("None Found");
        }
    }

    @Override
    public int getItemCount() {
        if(courseClassList != null){
            return courseClassList.size();
        }
        else {
            return 0;
        }

    }
    @SuppressLint("NotifyDataSetChanged")
    public void setCourseClassList(List<CourseClass> courses){
        courseClassList = courses;
        notifyDataSetChanged();
    }
}
