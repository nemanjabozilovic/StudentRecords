package com.example.studentrecords.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.studentrecords.R;
import com.example.studentrecords.data.models.Course;

import java.util.List;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.CourseViewHolder> {
    private List<Course> courses;
    private OnCourseClickListener listener;

    public CoursesAdapter(List<Course> courses, OnCourseClickListener listener) {
        this.courses = courses;
        this.listener = listener;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
        notifyDataSetChanged();
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
        Course course = courses.get(position);
        holder.tvCourseName.setText(course.getCourseName());

        holder.btnDelete.setOnClickListener(v -> listener.onDeleteClick(course.getId()));

        holder.itemView.setOnClickListener(v -> listener.onCourseClick(course));
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public interface OnCourseClickListener {
        void onDeleteClick(long courseId);
        void onCourseClick(Course course);
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView tvCourseName;
        ImageView btnDelete;

        public CourseViewHolder(View itemView) {
            super(itemView);
            tvCourseName = itemView.findViewById(R.id.tvCourseName);
            btnDelete = itemView.findViewById(R.id.btnDeleteCourse);
        }
    }
}