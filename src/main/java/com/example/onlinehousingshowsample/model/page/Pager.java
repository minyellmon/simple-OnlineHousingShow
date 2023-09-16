package com.example.onlinehousingshowsample.model.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pager {

    private int current;

    private int size;

    private long totalCount;

    private long totalPage;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int current;
        private int size;
        private long totalCount;
        private long totalPage;

        public Builder current(int data) {
            this.current = data;
            return this;
        }

        public Builder size(int data) {
            this.size = data;
            return this;
        }

        public Builder totalCount(long data) {
            this.totalCount = data;
            return this;
        }

        public Builder totalPage(long data) {
            this.totalPage = data;
            return this;
        }

        public Pager build() {
            return new Pager(current, size, totalCount, totalPage);
        }


    }
}