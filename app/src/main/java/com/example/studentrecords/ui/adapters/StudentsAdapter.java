package com.example.studentrecords.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.studentrecords.R;
import com.example.studentrecords.data.models.Student;

import java.util.List;

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.StudentViewHolder> {
    private List<Student> students;
    private OnStudentClickListener listener;

    public StudentsAdapter(List<Student> students, OnStudentClickListener listener) {
        this.students = students;
        this.listener = listener;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
        notifyDataSetChanged();
    }

    @Override
    public StudentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StudentViewHolder holder, int position) {
        Student student = students.get(position);
        holder.tvStudentName.setText(student.getFullName());
        holder.tvStudentGPA.setText("GPA: " + student.getGpa());

        holder.itemView.setOnClickListener(v -> listener.onStudentClick(student));
        holder.btnDelete.setOnClickListener(v -> listener.onDeleteClick(student.getId()));
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public interface OnStudentClickListener {
        void onStudentClick(Student student);
        void onDeleteClick(long studentId);
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView tvStudentName, tvStudentGPA;
        ImageView btnDelete;

        public StudentViewHolder(View itemView) {
            super(itemView);
            tvStudentName = itemView.findViewById(R.id.tvStudentName);
            tvStudentGPA = itemView.findViewById(R.id.tvStudentGPA);
            btnDelete = itemView.findViewById(R.id.btnDeleteStudent);
        }
    }
}