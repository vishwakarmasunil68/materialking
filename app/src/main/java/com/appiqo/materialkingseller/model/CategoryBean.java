package com.appiqo.materialkingseller.model;

import java.util.List;

/**
 * Created by Warlock on 1/26/2018.
 */

public class CategoryBean {

    private int status;
    private String message;
    private List<ResultBean> result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        private String id;
        private String name;
        private String image;
        private String isParent;
        private List<SubBean> sub;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getIsParent() {
            return isParent;
        }

        public void setIsParent(String isParent) {
            this.isParent = isParent;
        }

        public List<SubBean> getSub() {
            return sub;
        }

        public void setSub(List<SubBean> sub) {
            this.sub = sub;
        }

        public static class SubBean {
            /**
             * id : 25
             * name : Power and Backup System
             * image : e1d53c8e75c9cce36c82838725e268ad.jpg
             * isParent : 7
             */

            private String id;
            private String name;
            private String image;
            private String isParent;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getIsParent() {
                return isParent;
            }

            public void setIsParent(String isParent) {
                this.isParent = isParent;
            }
        }
    }
}
